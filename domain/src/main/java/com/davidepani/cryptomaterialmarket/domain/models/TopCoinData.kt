package com.davidepani.cryptomaterialmarket.domain.models

import java.time.LocalDateTime

data class TopCoinData(
    val topCoins: List<CoinWithMarketData>,
    val lastUpdate: LocalDateTime
)
