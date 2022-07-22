package com.davidepani.cryptomaterialmarket.data.features.topcoins.local

import androidx.room.*
import com.davidepani.cryptomaterialmarket.data.features.topcoins.local.models.TopCoinEntity
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