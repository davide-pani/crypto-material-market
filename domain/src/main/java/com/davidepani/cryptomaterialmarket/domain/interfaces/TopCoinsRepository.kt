package com.davidepani.cryptomaterialmarket.domain.interfaces

import com.davidepani.cryptomaterialmarket.domain.models.CoinWithMarketData
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import kotlinx.coroutines.flow.Flow

interface TopCoinsRepository {

    fun getTopCoinsFlow(): Flow<List<CoinWithMarketData>>

    suspend fun refreshTopCoins(
        numCoins: Int,
        currency: Currency,
        ordering: Ordering
    ): Result<List<CoinWithMarketData>>

    private fun sortCoins() {

    }

}