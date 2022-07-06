package com.davidepani.cryptomaterialmarket.data.mappers

import com.davidepani.cryptomaterialmarket.data.models.CoinApiResponse
import com.davidepani.cryptomaterialmarket.domain.entities.Coin
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun mapCoin(coinResponse: CoinApiResponse): Coin {
        return Coin(
            name = coinResponse.name,
            price = coinResponse.currentPrice,
            marketCap = coinResponse.marketCap,
            image = coinResponse.image
        )
    }

    fun mapCoinsList(coinsListResponse: List<CoinApiResponse>): List<Coin> {
        return coinsListResponse.map { mapCoin(it) }
    }

}