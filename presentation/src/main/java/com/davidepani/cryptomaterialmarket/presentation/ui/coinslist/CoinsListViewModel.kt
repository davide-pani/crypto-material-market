package com.davidepani.cryptomaterialmarket.presentation.ui.coinslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import com.davidepani.cryptomaterialmarket.domain.models.SettingsConfiguration
import com.davidepani.cryptomaterialmarket.domain.models.State
import com.davidepani.cryptomaterialmarket.domain.usecases.GetCoinsListUseCase
import com.davidepani.cryptomaterialmarket.domain.usecases.UpdateSettingsUseCase
import com.davidepani.cryptomaterialmarket.presentation.mappers.UiMapper
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    private val getCoinsListUseCase: GetCoinsListUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    private val settingsConfiguration: SettingsConfiguration,
    private val mapper: UiMapper
) : ViewModel() {

    val itemsList = getCoinsListUseCase.list.map {
        mapper.mapCoinUiItemsList(it)
    }
    var state by mutableStateOf<CoinsListUiState>(CoinsListUiState.Idle)
        private set

    init {
        getCoinsListUseCase.state.onEach {
            state = when (it) {
                is State.Error -> CoinsListUiState.Error(false, mapper.mapErrorToUiMessage(it.error))
                State.Idle -> CoinsListUiState.Idle
                is State.Loading -> CoinsListUiState.Loading(it.initial)
                State.Refresh -> CoinsListUiState.Refreshing
            }

        }

        getNextPage()
    }


    fun getNextPage() {
        getCoinsListUseCase(viewModelScope)
    }

    private fun refresh() {
        getCoinsListUseCase(viewModelScope, true)
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