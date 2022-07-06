package com.davidepani.cryptomaterialmarket.domain.interfaces

import com.davidepani.cryptomaterialmarket.domain.entities.Coin

interface CoinsRepository {

    suspend fun retrieveCoinsList(): Result<List<Coin>>

}