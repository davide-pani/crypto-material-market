package com.davidepani.cryptomaterialmarket.presentation.ui.coinslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidepani.cryptomaterialmarket.domain.models.*
import com.davidepani.cryptomaterialmarket.domain.usecases.GetCoinsListFlowUseCase
import com.davidepani.cryptomaterialmarket.domain.usecases.GetCoinsListUseCase
import com.davidepani.cryptomaterialmarket.domain.usecases.UpdateSettingsUseCase
import com.davidepani.cryptomaterialmarket.presentation.mappers.UiMapper
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListState
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    private val getCoinsListFlowUseCase: GetCoinsListFlowUseCase,
    private val getCoinsListUseCase: GetCoinsListUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    private val settingsConfiguration: SettingsConfiguration,
    private val mapper: UiMapper
) : ViewModel() {

    var state by mutableStateOf(
        CoinsListState(
            emptyList(),
            CoinsListUiState.Loading(true)
        )
    )
    private set


    init {
        viewModelScope.launch {
            getCoinsListFlowUseCase().map {
                mapper.mapCoinUiItemsList(it)
            }.catch {
                handleGetCoinsListResult(Result.Failure(it))
            }.collect {
                if (it.isNotEmpty()) {
                    state = state.copy(
                        coinsList = it,
                        state = CoinsListUiState.Idle
                    )
                }
            }
        }

        load()
    }

    private fun load() {
        state = state.copy(
            state = CoinsListUiState.Loading(true)
        )

        viewModelScope.launch {
            val result = getCoinsListUseCase(page = 1)
            handleGetCoinsListResult(result)
        }
    }

    private fun refresh() {
        state = state.copy(
            state = CoinsListUiState.Refreshing
        )

        viewModelScope.launch {
            val result = getCoinsListUseCase(page = 1)
            handleGetCoinsListResult(result)
        }
    }

    private fun handleGetCoinsListResult(result: Result<List<Coin>>) {
        state = when (result) {
            is Result.Success -> {
                state.copy(
                    state = CoinsListUiState.Idle
                )
            }
            is Result.Failure -> {
                state.copy(
                    state = CoinsListUiState.Error(message = mapper.mapErrorToUiMessage(result.error))
                )
            }
        }
    }

    fun onRetryClick() {
        refresh()
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