package com.davidepani.cryptomaterialmarket.presentation.models

data class CoinsListState(
    val refreshing: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null,
    val listFullyLoaded: Boolean = false
)

