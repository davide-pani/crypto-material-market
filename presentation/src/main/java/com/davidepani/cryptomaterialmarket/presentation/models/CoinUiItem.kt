package com.davidepani.cryptomaterialmarket.presentation.models

import androidx.compose.ui.graphics.Color
import com.davidepani.cryptomaterialmarket.domain.models.Coin

data class CoinUiItem(
    val name: String,
    val symbol: String,
    val imageUrl: String,
    val price: String,
    val marketCapRank: String,
    val priceChangePercentage: String?,
    val trendColor: Color?,
    val sparklineData: List<DataPoint>?,
    val businessModelReference: Coin
)