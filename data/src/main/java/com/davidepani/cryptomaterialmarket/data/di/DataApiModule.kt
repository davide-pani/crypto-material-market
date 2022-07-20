package com.davidepani.cryptomaterialmarket.data.di

import android.content.Context
import androidx.room.Room
import com.davidepani.cryptomaterialmarket.data.api.CoinGeckoApiService
import com.davidepani.cryptomaterialmarket.data.local.CoinsDao
import com.davidepani.cryptomaterialmarket.data.local.CoinsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataApiModule {

    @Provides
    @Singleton
    fun provideCoinGeckoApiService(): CoinGeckoApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(CoinGeckoApiService.BASE_URL)
        .build()
        .create(CoinGeckoApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CoinsDatabase {
        return Room.databaseBuilder(
            context,
            CoinsDatabase::class.java,
            "coins.db"
        ).build()
    }

    @Provides
    fun provideAnimalsDao(
        coinsDatabase: CoinsDatabase
    ): CoinsDao = coinsDatabase.coinsDao()

}