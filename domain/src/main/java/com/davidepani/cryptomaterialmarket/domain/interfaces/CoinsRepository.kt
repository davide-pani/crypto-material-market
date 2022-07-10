package com.davidepani.cryptomaterialmarket.domain.interfaces

import com.davidepani.cryptomaterialmarket.domain.models.Coin
import com.davidepani.cryptomaterialmarket.domain.models.Result

interface CoinsRepository {

    suspend fun retrieveCoinsList(
        currency: String = "usd",
        numCoinsPerPage: Int = 100,
        page: Int = 1,
        includeSparkline7dData: Boolean = false
    ): Result<List<Coin>>

}