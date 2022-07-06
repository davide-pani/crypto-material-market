package com.davidepani.cryptomaterialmarket.domain.interfaces

import com.davidepani.cryptomaterialmarket.domain.entities.Coin
import com.davidepani.cryptomaterialmarket.domain.entities.Result

interface CoinsRepository {

    suspend fun retrieveCoinsList(): Result<List<Coin>>

}