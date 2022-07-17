package com.davidepani.cryptomaterialmarket.presentation.models

import android.os.Parcelable
import androidx.compose.ui.graphics.Color
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CoinUiItem(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val price: String,
    val marketCapRank: String,
    val priceChangePercentage: String?,
    val trendColor: @RawValue Color?,
    val sparklineData: List<DataPoint>?
) : Parcelable