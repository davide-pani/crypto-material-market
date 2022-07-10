package com.davidepani.cryptomaterialmarket.domain.models

data class Coin(
    val name: String,
    val symbol: String,
    val price: Double,
    val marketCap: Long,
    val marketCapRank: Int,
    val image: String,
    val priceChangePercentage7d: Double?,
    val sparkline7dData: List<Double>?
)
