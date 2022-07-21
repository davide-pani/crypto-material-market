package com.davidepani.cryptomaterialmarket.data.di

import com.davidepani.cryptomaterialmarket.data.api.coingecko.CoinGeckoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideCoinGeckoApiService(): CoinGeckoApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(CoinGeckoApiService.BASE_URL)
        .build()
        .create(CoinGeckoApiService::class.java)

}