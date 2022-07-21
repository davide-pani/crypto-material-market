package com.davidepani.cryptomaterialmarket.data.api.coingecko

import com.davidepani.cryptomaterialmarket.data.api.coingecko.models.CoinGeckoMarketsDto
import retrofit2.http.GET
import retrofit2.http.Query


interface CoinGeckoApiService {

    @GET("/api/v3/coins/markets")
    suspend fun getCoinsMarkets(@Query("vs_currency") currency: String = "usd", @Query("page") page: Int = 1, @Query("per_page") numCoinsPerPage: Int = 100, @Query("order") order: String = "market_cap_desc", @Query("sparkline") includeSparkline7dData: Boolean = false, @Query("price_change_percentage") priceChangePercentageIntervals: String = ""): List<CoinGeckoMarketsDto>


    companion object {
        const val BASE_URL = "https://api.coingecko.com"
    }

}