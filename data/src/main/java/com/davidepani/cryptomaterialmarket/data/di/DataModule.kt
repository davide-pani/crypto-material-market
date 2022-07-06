package com.davidepani.cryptomaterialmarket.data.di

import com.davidepani.cryptomaterialmarket.data.repositories.CoinGeckoCoinsRepository
import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindCoinsRepository(coinGeckoCoinsRepository: CoinGeckoCoinsRepository): CoinsRepository

}