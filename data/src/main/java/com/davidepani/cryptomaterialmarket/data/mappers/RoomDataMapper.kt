package com.davidepani.cryptomaterialmarket.data.mappers

import com.davidepani.cryptomaterialmarket.data.features.topcoins.local.models.TopCoinEntity
import com.davidepani.cryptomaterialmarket.domain.models.CoinMarketData
import com.davidepani.cryptomaterialmarket.domain.models.CoinWithMarketData
import com.davidepani.kotlinextensions.deserializeAsJson
import com.davidepani.kotlinextensions.serializeToJsonString
import com.davidepani.kotlinextensions.utils.serialization.SerializationManager
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class RoomDataMapper @Inject constructor(
    private val serializationManager: SerializationManager
) {

    private fun mapCoinWithMarketDataEntity(coinWithMarketData: CoinWithMarketData): TopCoinEntity {
        return with(coinWithMarketData) {
            TopCoinEntity(
                id = id,
                name = name,
                symbol = symbol,
                image = image,
                rank = rank,
                lastUpdate = lastUpdate.toEpochSecond(ZoneOffset.UTC),
                price = marketData.price,
                marketCap = marketData.marketCap,
                priceChangePercentage = marketData.priceChangePercentage,
                sparklineData = marketData.sparklineData?.serializeToJsonString(serializationManager),
            )
        }
    }

    fun mapCoinWithMarketDataEntityList(coinsList: List<CoinWithMarketData>): List<TopCoinEntity> {
        return coinsList.map { mapCoinWithMarketDataEntity(it) }
    }

    private fun mapCoinWithMarketData(coinWithMarketDataEntity: TopCoinEntity): CoinWithMarketData {
        return with(coinWithMarketDataEntity) {
            CoinWithMarketData(
                id = id,
                name = name,
                symbol = symbol,
                image = image,
                rank = rank,
                lastUpdate = LocalDateTime.ofEpochSecond(lastUpdate, 0, ZoneOffset.UTC),
                marketData = CoinMarketData(
                    price = price,
                    marketCap = marketCap,
                    priceChangePercentage = priceChangePercentage,
                    sparklineData = sparklineData?.deserializeAsJson(serializationManager),
                )
            )
        }
    }

    fun mapCoinWithMarketDataList(coinsList: List<TopCoinEntity>): List<CoinWithMarketData> {
        return coinsList.map { mapCoinWithMarketData(it) }
    }

}