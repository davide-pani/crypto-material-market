package com.davidepani.cryptomaterialmarket.presentation.models

import androidx.compose.ui.graphics.Color

sealed class CoinsListStateItems {
    object LoadMore : CoinsListStateItems()
    data class Loading(val startIndex: Int, val endIndex: Int) : CoinsListStateItems()
    data class Error(val message: String) : CoinsListStateItems()
    data class CoinUiItem(
        val name: String,
        val symbol: String,
        val imageUrl: String,
        val price: String,
        val marketCapRank: String,
        val priceChangePercentage7d: String?,
        val trendColor: Color?,
        val sparkline7dData: List<DataPoint>?
    ) : CoinsListStateItems()
}