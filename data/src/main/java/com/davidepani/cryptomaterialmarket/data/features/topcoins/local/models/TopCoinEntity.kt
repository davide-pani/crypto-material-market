package com.davidepani.cryptomaterialmarket.data.features.topcoins.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "top_coins")
data class TopCoinEntity(
    @PrimaryKey val id: String,

    val name: String,
    val symbol: String,
    val image: String,
    val price: Double,
    val marketCap: Long,
    val priceChangePercentage: Double,
    val sparklineData: String?,
    val rank: Int,
    val lastUpdate: Long
)
