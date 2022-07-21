package com.davidepani.cryptomaterialmarket.data.repositories

import com.davidepani.cryptomaterialmarket.domain.interfaces.TopCoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.CoinWithMarketData
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TopCoinsRepositoryImpl @Inject constructor(
    private val remoteSource: TopCoinsRemoteDataSource,
    private val localSource: TopCoinsLocalDataSource,
    private val dispatchers: Dispatchers
) : TopCoinsRepository {

    private suspend fun persistCoins(coins: List<CoinWithMarketData>) {
        localSource.insertCoins(coins)
    }

    override fun getTopCoinsFlow(): Flow<List<CoinWithMarketData>> {
        return localSource.getAllCoinsFlow().distinctUntilChanged()
    }

    override suspend fun refreshTopCoins(numCoins: Int, currency: Currency, ordering: Ordering): Result<List<CoinWithMarketData>> {
        return Result.runCatching {
            withContext(dispatchers.IO) {
                val coinsList = remoteSource.retrieveTopCoinsWithMarketData(
                    currency = currency,
                    numCoins = numCoins,
                    ordering = Ordering.MarketCapDesc,
                ).getOrElse { throw it }

                val sortedCoinsList = sortCoins(
                    coinsList = coinsList,
                    ordering = ordering
                )

                localSource.deleteAllCoins()
                persistCoins(sortedCoinsList)

                sortedCoinsList
            }
        }
    }

    private fun sortCoins(coinsList: List<CoinWithMarketData>, ordering: Ordering): List<CoinWithMarketData> {
        val itemsList = coinsList.toMutableList()

        when (ordering) {
            Ordering.MarketCapAsc -> itemsList.sortByDescending { it.rank }
            Ordering.MarketCapDesc -> itemsList.sortBy { it.rank }
            Ordering.PriceAsc -> itemsList.sortBy { it.marketData.price }
            Ordering.PriceDesc -> itemsList.sortByDescending { it.marketData.price }
            Ordering.PriceChangeAsc -> itemsList.sortBy { it.marketData.priceChangePercentage }
            Ordering.PriceChangeDesc -> itemsList.sortByDescending { it.marketData.priceChangePercentage }
            Ordering.NameAsc -> itemsList.sortBy { it.name }
            Ordering.NameDesc -> itemsList.sortByDescending { it.name }
        }

        return itemsList
    }

}

interface TopCoinsLocalDataSource {

    fun getAllCoinsFlow(): Flow<List<CoinWithMarketData>>
    suspend fun insertCoins(coinsList: List<CoinWithMarketData>)
    suspend fun deleteAllCoins()

}

interface TopCoinsRemoteDataSource {

    suspend fun retrieveTopCoinsWithMarketData(
        numCoins: Int,
        currency: Currency,
        ordering: Ordering,
    ): Result<List<CoinWithMarketData>>

}