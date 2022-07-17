package com.davidepani.cryptomaterialmarket.presentation.ui.coinslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidepani.cryptomaterialmarket.domain.models.*
import com.davidepani.cryptomaterialmarket.domain.usecases.GetCoinsListUseCase
import com.davidepani.cryptomaterialmarket.domain.usecases.UpdateSettingsUseCase
import com.davidepani.cryptomaterialmarket.presentation.mappers.UiMapper
import com.davidepani.cryptomaterialmarket.presentation.models.CoinUiItem
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    private val getCoinsListUseCase: GetCoinsListUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    private val settingsConfiguration: SettingsConfiguration,
    private val mapper: UiMapper
) : ViewModel() {

    val itemsList = mutableStateListOf<CoinUiItem>()
    var state by mutableStateOf<CoinsListUiState>(CoinsListUiState.Idle)
        private set

    private var numLoadedPages = 0
    private var loadingJob: Job? = null
    private var refreshJob: Job? = null

    init {
        getNextPage()
    }


    fun getNextPage() {
        if (state is CoinsListUiState.Loading || state is CoinsListUiState.Refreshing || state is CoinsListUiState.FullyLoadedList) {
            return
        }

        val pageToLoad = numLoadedPages + 1

        state = CoinsListUiState.Loading(initial = itemsList.isEmpty())

        loadingJob = viewModelScope.launch {
            val result = getCoinsListUseCase(page = pageToLoad)
            handleGetCoinsListResult(result)
            loadingJob = null
        }
    }

    private fun refresh() {
        state = CoinsListUiState.Refreshing

        val oldRefreshJob = refreshJob

        refreshJob = viewModelScope.launch {
            loadingJob?.cancelAndJoin()
            oldRefreshJob?.cancelAndJoin()

            // Ensures that after having cancelled all the eventual pending jobs
            // the state is Refreshing
            state = CoinsListUiState.Refreshing

            val result = getCoinsListUseCase(page = 1)

            itemsList.clear()
            numLoadedPages = 0
            handleGetCoinsListResult(result)
            refreshJob = null
        }
    }

    private fun handleGetCoinsListResult(result: Result<List<Coin>>) {
        when (result) {
            is Result.Success -> {
                itemsList.addAll(mapper.mapCoinUiItemsList(result.value))

                state = if (result.value.size < settingsConfiguration.coinsListPageSize) {
                    CoinsListUiState.FullyLoadedList
                } else {
                    numLoadedPages++
                    CoinsListUiState.Idle
                }
            }
            is Result.Failure -> {
                // CancellationExceptions are not displayed as errors to the user
                // as they are handled internally by this class
                if (result.error !is CancellationException) {
                    state = CoinsListUiState.Error(
                        initial = itemsList.isEmpty(),
                        message = mapper.mapErrorToUiMessage(result.error)
                    )
                }
            }
        }
    }

    fun onRetryClick() {
        getNextPage()
    }

    fun onSwipeRefresh() {
        refresh()
    }

    fun updateSettings() {
        updateSettingsUseCase(currency = Currency.BTC, ordering = Ordering.MarketCapDesc)
        refresh()
    }

    /*
    fun sortCoinsList(ordering: Ordering) {
        when (ordering) {
            Ordering.MarketCapAsc -> itemsList.sortBy { it.businessModelReference.marketCap }
            Ordering.MarketCapDesc -> itemsList.sortByDescending { it.businessModelReference.marketCap }
            Ordering.PriceAsc -> itemsList.sortBy { it.businessModelReference.price }
            Ordering.PriceDesc -> itemsList.sortByDescending { it.businessModelReference.price }
            Ordering.PriceChangeAsc -> itemsList.sortBy { it.businessModelReference.priceChangePercentage }
            Ordering.PriceChangeDesc -> itemsList.sortByDescending { it.businessModelReference.priceChangePercentage }
            Ordering.NameAsc -> itemsList.sortBy { it.name }
            Ordering.NameDesc -> itemsList.sortByDescending { it.name }
        }
    }

     */

}