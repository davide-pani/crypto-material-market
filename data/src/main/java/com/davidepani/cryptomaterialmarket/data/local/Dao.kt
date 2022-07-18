package com.davidepani.cryptomaterialmarket.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davidepani.cryptomaterialmarket.data.models.CoinApiResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT * FROM coins")
    fun getAllCoins(): Flow<List<CoinApiResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(coin: CoinApiResponse)

}