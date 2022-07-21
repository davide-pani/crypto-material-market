package com.davidepani.cryptomaterialmarket.data.repositories

import com.davidepani.cryptomaterialmarket.data.api.CoinGeckoApiService
import com.davidepani.cryptomaterialmarket.data.local.CoinsDao
import com.davidepani.cryptomaterialmarket.data.mappers.DataMapper
import com.davidepani.cryptomaterialmarket.data.models.CoinApiResponse
import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.Coin
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import com.davidepani.cryptomaterialmarket.domain.models.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinGeckoCoinsRepository @Inject constructor(
    private val coinGeckoApiService: CoinGeckoApiService,
    private val localSource: CoinsDao,
    private val mapper: DataMapper,
    private val dispatchers: Dispatchers
) : CoinsRepository {

    override suspend fun retrieveCoinsList(
        currency: Currency,
        numCoinsPerPage: Int,
        page: Int,
        ordering: Ordering,
        includeSparklineData: Boolean
    ): Result<List<Coin>> {
        return try {
            val coinsList = withContext(dispatchers.IO) {
                val coinsList = coinGeckoApiService.getCoinsMarkets(
                    currency = mapper.mapCurrencyToCoinGeckoApiValue(currency),
                    page = page,
                    numCoinsPerPage = numCoinsPerPage,
                    order = mapper.mapOrderingToCoinGeckoApiValue(ordering),
                    includeSparkline7dData = includeSparklineData,
                    priceChangePercentageIntervals = "7d"
                )

                localSource.deleteAllCoins()
                persistCoins(coinsList)
                mapper.mapCoinsList(coinsList)
            }

            Result.Success(coinsList)
        } catch(e: Exception) {
            Result.Failure(e)
        }
    }

    override fun getAllCoins(): Flow<List<Coin>> {
        return localSource.getAllCoins()
            .distinctUntilChanged()
            .map {
                mapper.mapCoinsList(it)
            }
    }

    private suspend fun persistCoins(coins: List<CoinApiResponse>) {
        localSource.insertCoins(coins)
    }

}