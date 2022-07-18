package com.davidepani.cryptomaterialmarket.domain.models

sealed class State {
    data class Loading(val initial: Boolean = false) : State()
    object Idle : State()
    object Refresh : State()
    data class Error(val error: Throwable) : State()
}