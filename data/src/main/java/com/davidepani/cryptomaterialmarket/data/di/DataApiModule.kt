package com.davidepani.cryptomaterialmarket.data.di

import android.content.Context
import androidx.room.Room
import com.davidepani.cryptomaterialmarket.data.api.CoinGeckoApiService
import com.davidepani.cryptomaterialmarket.data.local.Dao
import com.davidepani.cryptomaterialmarket.data.local.Database
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
    fun provideDao(db: Database): Dao {
        return db.dao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): Database {
        return Room.databaseBuilder(
            appContext,
            Database::class.java,
            "database"
        ).build()
    }

}