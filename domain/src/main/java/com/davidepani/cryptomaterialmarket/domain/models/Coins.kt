package com.davidepani.cryptomaterialmarket.domain.models

import java.time.LocalDateTime

abstract class BaseCoin(
    open val id: String,
    open val name: String,
    open val symbol: String,
    open val image: String
)

abstract class BaseCoinWithMarketData(
    id: String,
    name: String,
    symbol: String,
    image: String,
    open val marketData: CoinMarketData
) : BaseCoin(
    id = id,
    name = name,
    symbol = symbol,
    image = image
)

data class CoinMarketData(
    val price: Double,
    val marketCap: Long,
    val priceChangePercentage: Double,
    val sparklineData: List<Double>?,
)

data class Coin(
    override val id: String,
    override val name: String,
    override val symbol: String,
    override val image: String,
    val rank: Int
) : BaseCoin(
    id = id,
    name = name,
    symbol = symbol,
    image = image
)

data class CoinWithMarketData(
    override val id: String,
    override val name: String,
    override val symbol: String,
    override val image: String,
    override val marketData: CoinMarketData,
    val rank: Int,
    val lastUpdate: LocalDateTime
) : BaseCoinWithMarketData(
    id = id,
    name = name,
    symbol = symbol,
    image = image,
    marketData = marketData
)