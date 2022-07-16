package com.davidepani.cryptomaterialmarket.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Screen : Parcelable {

    @Parcelize
    object CoinsList : Screen()

    @Parcelize
    data class CoinDetail(val coinId: String) : Screen()

}
