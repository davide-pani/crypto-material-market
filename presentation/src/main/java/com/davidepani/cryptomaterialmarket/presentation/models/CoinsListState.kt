package com.davidepani.cryptomaterialmarket.presentation.models

data class CoinsListState(
    val coinsList: List<CoinUiItem>,
    val state: CoinsListUiState
)

sealed class CoinsListUiState {
    object Idle : CoinsListUiState()
    object Refreshing : CoinsListUiState()
    data class Loading(val initial: Boolean) : CoinsListUiState()
    data class Error(val message: String) : CoinsListUiState()
}


