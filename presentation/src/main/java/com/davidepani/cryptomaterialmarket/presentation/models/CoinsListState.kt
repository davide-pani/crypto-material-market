package com.davidepani.cryptomaterialmarket.presentation.models

data class CoinsListState(
    val coinsList: List<CoinUiItem>,
    val lastUpdateDate: String,
    val state: CoinsListUiState
)

sealed class CoinsListUiState {
    object Idle : CoinsListUiState()
    object Refreshing : CoinsListUiState()
    data class Error(val message: String) : CoinsListUiState()
}


