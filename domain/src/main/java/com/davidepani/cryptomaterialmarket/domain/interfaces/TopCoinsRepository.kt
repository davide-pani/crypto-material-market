package com.davidepani.cryptomaterialmarket.domain.interfaces

import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import com.davidepani.cryptomaterialmarket.domain.models.TopCoinData
import kotlinx.coroutines.flow.Flow

interface TopCoinsRepository {

    fun getTopCoinsFlow(): Flow<TopCoinData>

    suspend fun refreshTopCoins(
        numCoins: Int,
        currency: Currency,
        ordering: Ordering
    ): Result<TopCoinData>

}