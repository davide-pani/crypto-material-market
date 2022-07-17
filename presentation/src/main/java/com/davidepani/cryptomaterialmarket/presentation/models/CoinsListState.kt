package com.davidepani.cryptomaterialmarket.presentation.models

sealed class CoinsListUiState {
    object Idle : CoinsListUiState()
    object Refreshing : CoinsListUiState()
    data class Loading(val initial: Boolean) : CoinsListUiState()
    data class Error(val initial: Boolean, val message: String) : CoinsListUiState()
    object FullyLoadedList : CoinsListUiState()
}


