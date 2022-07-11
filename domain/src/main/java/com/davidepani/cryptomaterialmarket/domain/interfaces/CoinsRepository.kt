package com.davidepani.cryptomaterialmarket.domain.interfaces

import com.davidepani.cryptomaterialmarket.domain.models.Coin
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import com.davidepani.cryptomaterialmarket.domain.models.Result

interface CoinsRepository {

    suspend fun retrieveCoinsList(
        currency: Currency = Currency.USD,
        numCoinsPerPage: Int = 100,
        page: Int = 1,
        ordering: Ordering = Ordering.MarketCapDesc,
        includeSparklineData: Boolean = false
    ): Result<List<Coin>>

}