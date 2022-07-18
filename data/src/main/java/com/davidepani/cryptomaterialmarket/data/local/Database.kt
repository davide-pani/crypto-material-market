package com.davidepani.cryptomaterialmarket.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.davidepani.cryptomaterialmarket.data.models.CoinApiResponse

@Database(entities = [CoinApiResponse::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun dao(): Dao

}