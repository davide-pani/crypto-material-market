package com.davidepani.cryptomaterialmarket.domain.interfaces

import com.davidepani.cryptomaterialmarket.domain.models.*
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {

    suspend fun retrieveCoinsList(
        currency: Currency = Currency.USD,
        numCoinsPerPage: Int = 100,
        page: Int = 1,
        ordering: Ordering = Ordering.MarketCapDesc,
        includeSparklineData: Boolean = false
    ): Result<List<Coin>>

    fun getAllCoins(): Flow<List<Coin>>

}