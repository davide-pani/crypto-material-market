package com.davidepani.cryptomaterialmarket.data.di

import com.davidepani.cryptomaterialmarket.data.features.topcoins.TopCoinsLocalDataSource
import com.davidepani.cryptomaterialmarket.data.features.topcoins.TopCoinsRemoteDataSource
import com.davidepani.cryptomaterialmarket.data.features.topcoins.TopCoinsRepositoryImpl
import com.davidepani.cryptomaterialmarket.data.features.topcoins.local.RoomTopCoinsLocalDataSource
import com.davidepani.cryptomaterialmarket.data.features.topcoins.remote.CoinGeckoTopCoinsRemoteDataSource
import com.davidepani.cryptomaterialmarket.domain.interfaces.TopCoinsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindTopCoinsRepository(topCoinsRepositoryImpl: TopCoinsRepositoryImpl): TopCoinsRepository

    @Binds
    abstract fun bindTopCoinsRemoteDataSource(coinGeckoCoinsRepository: CoinGeckoTopCoinsRemoteDataSource): TopCoinsRemoteDataSource

    @Binds
    abstract fun bindTopCoinsLocalDataSource(roomTopCoinsLocalDataSource: RoomTopCoinsLocalDataSource): TopCoinsLocalDataSource

}