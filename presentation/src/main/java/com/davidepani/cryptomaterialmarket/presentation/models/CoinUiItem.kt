package com.davidepani.cryptomaterialmarket.presentation.models

import androidx.compose.ui.graphics.Color

data class CoinUiItem(
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val price: String,
    val marketCapRank: String,
    val priceChangePercentage7d: String?,
    val trendColor: Color?,
    val sparkline7dData: List<DataPoint>?
)