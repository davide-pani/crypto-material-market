package com.davidepani.cryptomaterialmarket.data.local

import com.davidepani.cryptomaterialmarket.data.mappers.RoomDataMapper
import com.davidepani.cryptomaterialmarket.data.repositories.TopCoinsLocalDataSource
import com.davidepani.cryptomaterialmarket.domain.models.CoinWithMarketData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomTopCoinsLocalDataSource @Inject constructor(
    private val topCoinsDao: TopCoinsDao,
    private val mapper: RoomDataMapper
) : TopCoinsLocalDataSource {

    override fun getAllCoinsFlow(): Flow<List<CoinWithMarketData>> {
        return topCoinsDao.getAllCoins().map {
            mapper.mapCoinWithMarketDataList(it)
        }
    }

    override suspend fun insertCoins(coinsList: List<CoinWithMarketData>) {
        topCoinsDao.insertCoins(
            mapper.mapCoinWithMarketDataEntityList(coinsList)
        )
    }

    override suspend fun deleteAllCoins() {
        topCoinsDao.deleteAllCoins()
    }

}