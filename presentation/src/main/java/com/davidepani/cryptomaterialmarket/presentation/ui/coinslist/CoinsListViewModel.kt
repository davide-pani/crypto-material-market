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
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
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
    var state by mutableStateOf(CoinsListState())
        private set

    private var numLoadedPages = 0
    private var loadingJob: Job? = null
    private var refreshJob: Job? = null

    init {
        getNextPage()
    }


    fun getNextPage() {
        if (state.loading || state.refreshing || state.listFullyLoaded) {
            return
        }

        val pageToLoad = numLoadedPages + 1

        state = state.copy(loading = true)

        loadingJob = viewModelScope.launch {
            val result = getCoinsListUseCase(page = pageToLoad)

            handleGetCoinsListResult(result)
            state = state.copy(loading = false)

            loadingJob = null
        }
    }

    private fun refresh() {
        loadingJob?.cancel()
        refreshJob?.cancel()
        state = state.copy(refreshing = true)

        refreshJob = viewModelScope.launch {
            val result = getCoinsListUseCase(page = 1)

            itemsList.clear()
            numLoadedPages = 0
            state = state.copy(listFullyLoaded = false)

            handleGetCoinsListResult(result)
            state = state.copy(refreshing = false)
            state = state.copy(loading = false)

            refreshJob = null
        }
    }

    private fun handleGetCoinsListResult(result: Result<List<Coin>>) {
        when (result) {
            is Result.Success -> {
                if (result.value.size < settingsConfiguration.coinsListPageSize) {
                    state = state.copy(listFullyLoaded = true)
                } else {
                    numLoadedPages++
                }

                itemsList.addAll(mapper.mapCoinUiItemsList(result.value))
            }
            is Result.Failure -> {
                // CancellationExceptions are not displayed as errors to the user
                // as they are handled internally by this class
                if (result.error !is CancellationException) {
                    state = state.copy(error = mapper.mapErrorToUiMessage(result.error))
                }
            }
        }
    }

    fun onRetryClick() {
        getNextPage()
    }

    fun onLoadMoreButtonClick() {
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