package com.davidepani.cryptomaterialmarket.data.mappers

import com.davidepani.cryptomaterialmarket.data.local.TopCoinEntity
import com.davidepani.cryptomaterialmarket.domain.models.CoinMarketData
import com.davidepani.cryptomaterialmarket.domain.models.CoinWithMarketData
import javax.inject.Inject

class RoomDataMapper @Inject constructor() {

    private fun mapCoinWithMarketDataEntity(coinWithMarketData: CoinWithMarketData): TopCoinEntity {
        return with(coinWithMarketData) {
            TopCoinEntity(
                id = id,
                name = name,
                symbol = symbol,
                image = image,
                rank = rank,
                lastUpdate = lastUpdate,
                price = marketData.price,
                marketCap = marketData.marketCap,
                priceChangePercentage = marketData.priceChangePercentage,
                sparklineData = marketData.sparklineData,
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
                lastUpdate = lastUpdate,
                marketData = CoinMarketData(
                    price = price,
                    marketCap = marketCap,
                    priceChangePercentage = priceChangePercentage,
                    sparklineData = sparklineData,
                )
            )
        }
    }

    fun mapCoinWithMarketDataList(coinsList: List<TopCoinEntity>): List<CoinWithMarketData> {
        return coinsList.map { mapCoinWithMarketData(it) }
    }

}