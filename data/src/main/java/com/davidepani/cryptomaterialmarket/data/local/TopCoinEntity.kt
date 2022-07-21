package com.davidepani.cryptomaterialmarket.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "top_coins")
data class TopCoinEntity(
    @PrimaryKey val id: String,

    val name: String,
    val symbol: String,
    val image: String,
    val price: Double,
    val marketCap: Long,
    val priceChangePercentage: Double,
    val sparklineData: List<Double>?,
    val rank: Int,
    val lastUpdate: LocalDateTime
)
