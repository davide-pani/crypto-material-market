package com.davidepani.cryptomaterialmarket.domain.interfaces

import androidx.paging.PagingData
import com.davidepani.cryptomaterialmarket.domain.models.*
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {

    suspend fun retrieveCoinsList(
        currency: Currency = Currency.USD,
        numCoinsPerPage: Int = 100,
        page: Int = 1,
        ordering: Ordering = Ordering.MarketCapDesc,
        includeSparklineData: Boolean = false
    ): Result<List<Coin>>

    fun retrieveCoinsListWithPaging(
        settingsConfiguration: SettingsConfiguration,
        pageSize: Int = 100,
        initialPageSize: Int = 100,
        prefetchDistance: Int = 20,
        includeSparklineData: Boolean = false
    ): Flow<PagingData<Coin>>

}