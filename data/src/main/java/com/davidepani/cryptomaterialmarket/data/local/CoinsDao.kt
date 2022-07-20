package com.davidepani.cryptomaterialmarket.data.local

import androidx.room.*
import com.davidepani.cryptomaterialmarket.data.models.CoinApiResponse
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CoinsDao {

    @Transaction
    @Query("SELECT * FROM CoinApiResponse")
    abstract fun getAllCoins(): Flow<List<CoinApiResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCoins(coins: List<CoinApiResponse>)

    @Query("DELETE FROM CoinApiResponse")
    abstract suspend fun deleteAllCoins()

}