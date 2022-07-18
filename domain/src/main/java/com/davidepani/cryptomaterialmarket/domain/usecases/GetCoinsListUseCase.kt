package com.davidepani.cryptomaterialmarket.domain.usecases

import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.Coin
import com.davidepani.cryptomaterialmarket.domain.models.Result
import com.davidepani.cryptomaterialmarket.domain.models.SettingsConfiguration
import com.davidepani.cryptomaterialmarket.domain.models.State
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


class GetCoinsListUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val settingsConfiguration: SettingsConfiguration
) {

    private var numLoadedPages = 0
    private var loadingJob: Job? = null
    private var refreshJob: Job? = null

    var _state = MutableStateFlow<State>(State.Idle)
    val state: StateFlow<State> = _state

    val list = coinsRepository.retrieveCoinsListFlow()

    operator fun invoke(
        coroutineScope: CoroutineScope,
        refresh: Boolean = false
    ) {
        if (refresh) {
            refresh(coroutineScope)
        } else {
            loadNext(coroutineScope)
        }
    }

    private fun refresh(coroutineScope: CoroutineScope) {
        _state.value = State.Refresh

        val oldRefreshJob = refreshJob

        refreshJob = coroutineScope.launch {
            loadingJob?.cancelAndJoin()
            oldRefreshJob?.cancelAndJoin()

            // Ensures that after having cancelled all the eventual pending jobs
            // the state is Refreshing
            _state.value = State.Refresh

            val result = coinsRepository.retrieveCoinsList(
                currency = settingsConfiguration.getCurrency(),
                numCoinsPerPage = settingsConfiguration.coinsListPageSize,
                page = 1,
                ordering = settingsConfiguration.getOrdering(),
                includeSparklineData = true
            )


            numLoadedPages = 0
            handleGetCoinsListResult(result)
            refreshJob = null
        }

    }

    private fun loadNext(coroutineScope: CoroutineScope) {
        if (state.value is State.Loading || state.value is State.Refresh) {
            return
        }

        val pageToLoad = numLoadedPages + 1

        _state.value = State.Loading(initial = pageToLoad == 1)

        loadingJob = coroutineScope.launch {
            val result = coinsRepository.retrieveCoinsList(
                currency = settingsConfiguration.getCurrency(),
                numCoinsPerPage = settingsConfiguration.coinsListPageSize,
                page = pageToLoad,
                ordering = settingsConfiguration.getOrdering(),
                includeSparklineData = true
            )

            handleGetCoinsListResult(result)
            loadingJob = null
        }


    }

    private fun handleGetCoinsListResult(result: Result<List<Coin>>) {
        when (result) {
            is Result.Success -> {
                numLoadedPages++
                _state.value = State.Idle

            }
            is Result.Failure -> {
                // CancellationExceptions are not displayed as errors to the user
                // as they are handled internally by this class
                if (result.error !is CancellationException) {
                    _state.value = State.Error(
                        result.error
                    )
                }
            }
        }
    }

}