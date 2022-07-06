package com.davidepani.cryptomaterialmarket.domain.entities

data class Coin(
    val name: String,
    val price: Double,
    val marketCap: Long,
    val image: String
)
