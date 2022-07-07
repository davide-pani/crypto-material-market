package com.davidepani.cryptomaterialmarket.data.repositories

import com.davidepani.cryptomaterialmarket.data.api.CoinGeckoApiService
import com.davidepani.cryptomaterialmarket.data.mappers.DataMapper
import com.davidepani.cryptomaterialmarket.domain.entities.Coin
import com.davidepani.cryptomaterialmarket.domain.entities.Result
import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import javax.inject.Inject

class CoinGeckoCoinsRepository @Inject constructor(
    private val coinGeckoApiService: CoinGeckoApiService,
    private val mapper: DataMapper
) : CoinsRepository {

    override suspend fun retrieveCoinsList(
        currency: String,
        numCoinsPerPage: Int,
        page: Int,
        includeSparkline7dData: Boolean
    ): Result<List<Coin>> {
        return try {
            val coinsList = coinGeckoApiService.getCoinsMarkets(
                currency = currency,
                page = page,
                numCoinsPerPage = numCoinsPerPage,
                order = "market_cap_desc",
                includeSparkline7dData = includeSparkline7dData,
                priceChangePercentageIntervals = "7d"
            )

            Result.Success(mapper.mapCoinsList(coinsList))
        } catch(e: Exception) {
            Result.Failure(e)
        }
    }

}