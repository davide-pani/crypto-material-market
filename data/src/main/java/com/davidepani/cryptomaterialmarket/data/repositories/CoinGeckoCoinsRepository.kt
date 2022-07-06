package com.davidepani.cryptomaterialmarket.data.repositories

import com.davidepani.cryptomaterialmarket.data.api.CoinGeckoApiService
import com.davidepani.cryptomaterialmarket.data.mappers.DataMapper
import com.davidepani.cryptomaterialmarket.domain.entities.Coin
import com.davidepani.cryptomaterialmarket.domain.entities.Result
import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import javax.inject.Inject

class CoinGeckoCoinsRepository @Inject constructor(
    private val coinGeckoApiService: CoinGeckoApiService,
    private val mapper: DataMapper
) : CoinsRepository {

    override suspend fun retrieveCoinsList(): Result<List<Coin>> {
        return try {
            val coinsList = coinGeckoApiService.getCoinsMarkets(
                currency = "usd",
                numCoinsPerPage = 1,
                order = "market_cap_desc"
            )

            Result.Success(mapper.mapCoinsList(coinsList))
        } catch(e: Exception) {
            Result.Failure(e)
        }
    }

}