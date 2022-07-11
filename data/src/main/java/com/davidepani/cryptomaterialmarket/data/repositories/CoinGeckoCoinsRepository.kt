package com.davidepani.cryptomaterialmarket.data.repositories

import com.davidepani.cryptomaterialmarket.data.api.CoinGeckoApiService
import com.davidepani.cryptomaterialmarket.data.mappers.DataMapper
import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.Coin
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import com.davidepani.cryptomaterialmarket.domain.models.Result
import javax.inject.Inject

class CoinGeckoCoinsRepository @Inject constructor(
    private val coinGeckoApiService: CoinGeckoApiService,
    private val mapper: DataMapper
) : CoinsRepository {

    override suspend fun retrieveCoinsList(
        currency: Currency,
        numCoinsPerPage: Int,
        page: Int,
        ordering: Ordering,
        includeSparklineData: Boolean
    ): Result<List<Coin>> {
        return try {
            val coinsList = coinGeckoApiService.getCoinsMarkets(
                currency = mapper.mapCurrencyToCoinGeckoApiValue(currency),
                page = page,
                numCoinsPerPage = numCoinsPerPage,
                order = mapper.mapOrderingToCoinGeckoApiValue(ordering),
                includeSparkline7dData = includeSparklineData,
                priceChangePercentageIntervals = "7d"
            )

            Result.Success(mapper.mapCoinsList(coinsList))
        } catch(e: Exception) {
            Result.Failure(e)
        }
    }

}