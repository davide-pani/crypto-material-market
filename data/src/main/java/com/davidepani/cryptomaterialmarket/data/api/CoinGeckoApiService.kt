package com.davidepani.cryptomaterialmarket.data.api

import com.davidepani.cryptomaterialmarket.data.models.CoinApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface CoinGeckoApiService {

    @GET("/api/v3/coins/markets")
    suspend fun getCoinsMarkets(@Query("vs_currency") currency: String = "usd", @Query("page") page: Int = 1, @Query("per_page") numCoinsPerPage: Int = 100, @Query("order") order: String = "market_cap_desc", @Query("sparkline") includeSparkline7dData: Boolean = false, @Query("price_change_percentage") priceChangePercentageIntervals: String = ""): List<CoinApiResponse>


    companion object {
        const val BASE_URL = "https://api.coingecko.com"
    }

}