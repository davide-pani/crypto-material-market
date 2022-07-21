package com.davidepani.cryptomaterialmarket.domain.interfaces

import com.davidepani.cryptomaterialmarket.domain.models.CoinWithMarketData
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering

interface CoinsRepository {

    suspend fun retrieveCoinsWithMarketData(
        currency: Currency = Currency.USD,
        numCoinsPerPage: Int = 100,
        page: Int = 1,
        ordering: Ordering = Ordering.MarketCapDesc,
        includeSparklineData: Boolean = false
    ): Result<List<CoinWithMarketData>>

}