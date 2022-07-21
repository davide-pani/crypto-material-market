package com.davidepani.cryptomaterialmarket.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TopCoinsDao {

    @Transaction
    @Query("SELECT * FROM top_coins")
    abstract fun getAllCoins(): Flow<List<TopCoinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCoins(coins: List<TopCoinEntity>)

    @Query("DELETE FROM top_coins")
    abstract suspend fun deleteAllCoins()

}