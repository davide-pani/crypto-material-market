package com.davidepani.cryptomaterialmarket.presentation.models

import androidx.compose.ui.graphics.Color

data class CoinUiItem(
    val id: String,
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val price: String,
    val marketCapRank: String,
    val priceChangePercentage: String?,
    val trendColor: Color?,
    val sparklineData: List<DataPoint>?
)