package com.davidepani.cryptomaterialmarket.data.repositories

import com.davidepani.cryptomaterialmarket.data.api.coingecko.CoinGeckoApiService
import com.davidepani.cryptomaterialmarket.data.mappers.CoinGeckoDataMapper
import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.CoinWithMarketData
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import javax.inject.Inject

class CoinGeckoCoinsRepository @Inject constructor(
    private val coinGeckoApiService: CoinGeckoApiService,
    private val mapper: CoinGeckoDataMapper,
) : CoinsRepository, TopCoinsRemoteDataSource {

    override suspend fun retrieveCoinsWithMarketData(
        currency: Currency,
        numCoinsPerPage: Int,
        page: Int,
        ordering: Ordering,
        includeSparklineData: Boolean
    ): Result<List<CoinWithMarketData>> {
        return Result.runCatching {
            val coinsList = coinGeckoApiService.getCoinsMarkets(
                currency = mapper.mapCurrencyToCoinGeckoApiValue(currency),
                page = page,
                numCoinsPerPage = numCoinsPerPage,
                order = mapper.mapOrderingToCoinGeckoApiValue(ordering),
                includeSparkline7dData = includeSparklineData,
                priceChangePercentageIntervals = "7d"
            )

            mapper.mapCoinsWithMarketDataList(coinsList)
        }

    }

    override suspend fun retrieveTopCoinsWithMarketData(
        numCoins: Int,
        currency: Currency,
        ordering: Ordering
    ): Result<List<CoinWithMarketData>> {
        return retrieveCoinsWithMarketData(
            currency = currency,
            numCoinsPerPage = numCoins,
            page = 1,
            ordering = ordering,
            includeSparklineData = true
        )
    }

}