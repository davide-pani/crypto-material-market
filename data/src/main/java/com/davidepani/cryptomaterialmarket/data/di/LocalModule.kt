package com.davidepani.cryptomaterialmarket.data.di

import android.content.Context
import androidx.room.Room
import com.davidepani.cryptomaterialmarket.data.db.room.CoinsDatabase
import com.davidepani.cryptomaterialmarket.data.features.topcoins.local.TopCoinsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

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
    @Singleton
    fun provideAnimalsDao(
        coinsDatabase: CoinsDatabase
    ): TopCoinsDao = coinsDatabase.topCoinsDao()

}