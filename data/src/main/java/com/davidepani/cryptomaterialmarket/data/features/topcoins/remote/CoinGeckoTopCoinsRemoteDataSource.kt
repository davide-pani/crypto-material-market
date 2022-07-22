package com.davidepani.cryptomaterialmarket.data.features.topcoins.remote

import com.davidepani.cryptomaterialmarket.data.api.coingecko.CoinGeckoApiService
import com.davidepani.cryptomaterialmarket.data.features.topcoins.TopCoinsRemoteDataSource
import com.davidepani.cryptomaterialmarket.data.mappers.CoinGeckoDataMapper
import com.davidepani.cryptomaterialmarket.domain.models.CoinWithMarketData
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import javax.inject.Inject

class CoinGeckoTopCoinsRemoteDataSource @Inject constructor(
    private val coinGeckoApiService: CoinGeckoApiService,
    private val mapper: CoinGeckoDataMapper,
) : TopCoinsRemoteDataSource {

    override suspend fun retrieveTopCoinsWithMarketData(
        numCoins: Int,
        currency: Currency,
        ordering: Ordering
    ): Result<List<CoinWithMarketData>> {
        return Result.runCatching {
            val coinsList = coinGeckoApiService.getCoinsMarkets(
                currency = mapper.mapCurrencyToCoinGeckoApiValue(currency),
                page = 1,
                numCoinsPerPage = numCoins,
                order = mapper.mapOrderingToCoinGeckoApiValue(ordering),
                includeSparkline7dData = true,
                priceChangePercentageIntervals = "7d"
            )

            mapper.mapCoinsWithMarketDataList(coinsList)
        }
    }

}