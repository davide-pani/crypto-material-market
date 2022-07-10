package com.davidepani.cryptomaterialmarket.presentation.models

sealed class CoinsListState {
    object LoadMore : CoinsListState()
    data class Loading(val startIndex: Int, val endIndex: Int) : CoinsListState()
    data class Error(val message: String) : CoinsListState()
}
