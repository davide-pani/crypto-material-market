package com.davidepani.cryptomaterialmarket.presentation.ui.coinslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import com.davidepani.cryptomaterialmarket.domain.models.TopCoinData
import com.davidepani.cryptomaterialmarket.domain.usecases.GetTopCoinsFlowUseCase
import com.davidepani.cryptomaterialmarket.domain.usecases.RefreshTopCoinsUseCase
import com.davidepani.cryptomaterialmarket.domain.usecases.UpdateSettingsUseCase
import com.davidepani.cryptomaterialmarket.presentation.mappers.UiMapper
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListState
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    private val getTopCoinsFlowUseCase: GetTopCoinsFlowUseCase,
    private val refreshTopCoinsUseCase: RefreshTopCoinsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    private val mapper: UiMapper
) : ViewModel() {

    var state by mutableStateOf(
        CoinsListState(
            emptyList(),
            "",
            CoinsListUiState.Refreshing
        )
    )
    private set


    init {
        viewModelScope.launch {
            getTopCoinsFlowUseCase().collect {
                val uiData = mapper.mapTopCoinUiData(it)

                state = state.copy(
                    coinsList = uiData.topCoins,
                    lastUpdateDate = uiData.lastUpdate,
                    state = CoinsListUiState.Idle
                )
            }
        }

        refresh()
    }



    private fun refresh() {
        state = state.copy(
            state = CoinsListUiState.Refreshing
        )

        viewModelScope.launch {
            val result = refreshTopCoinsUseCase()
            handleGetCoinsListResult(result)
        }
    }

    private fun handleGetCoinsListResult(result: Result<TopCoinData>) {
        result.onSuccess {
            state = state.copy(
                state = CoinsListUiState.Idle
            )
        }.onFailure {
            state = state.copy(
                state = CoinsListUiState.Error(message = mapper.mapErrorToUiMessage(it))
            )
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

}