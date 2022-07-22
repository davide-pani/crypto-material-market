package com.davidepani.cryptomaterialmarket.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidepani.cryptomaterialmarket.data.features.topcoins.local.TopCoinsDao
import com.davidepani.cryptomaterialmarket.data.features.topcoins.local.models.TopCoinEntity

@Database(entities = [TopCoinEntity::class], version = 1, exportSchema = false)
abstract class CoinsDatabase : RoomDatabase() {

    abstract fun topCoinsDao(): TopCoinsDao

}