package com.davidepani.cryptomaterialmarket.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.davidepani.cryptomaterialmarket.data.api.CoinGeckoApiService
import com.davidepani.cryptomaterialmarket.data.mappers.DataMapper
import com.davidepani.cryptomaterialmarket.data.paging.CoinGeckoCoinsPagingSource
import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoinGeckoCoinsRepository @Inject constructor(
    private val coinGeckoApiService: CoinGeckoApiService,
    private val mapper: DataMapper
) : CoinsRepository {

    override suspend fun retrieveCoinsList(
        currency: Currency,
        numCoinsPerPage: Int,
        page: Int,
        ordering: Ordering,
        includeSparklineData: Boolean
    ): Result<List<Coin>> {
        return try {
            val coinsList = coinGeckoApiService.getCoinsMarkets(
                currency = mapper.mapCurrencyToCoinGeckoApiValue(currency),
                page = page,
                numCoinsPerPage = numCoinsPerPage,
                order = mapper.mapOrderingToCoinGeckoApiValue(ordering),
                includeSparkline7dData = includeSparklineData,
                priceChangePercentageIntervals = "7d"
            )

            Result.Success(mapper.mapCoinsList(coinsList))
        } catch(e: Exception) {
            Result.Failure(e)
        }
    }

    override fun retrieveCoinsListWithPaging(
        settingsConfiguration: SettingsConfiguration,
        pageSize: Int,
        initialPageSize: Int,
        prefetchDistance: Int,
        includeSparklineData: Boolean
    ): Flow<PagingData<Coin>> {
        return Pager(
            PagingConfig(
                pageSize = pageSize,
                initialLoadSize = initialPageSize,
                prefetchDistance = prefetchDistance
            )
        ) {
            CoinGeckoCoinsPagingSource(
                coinGeckoApiService = coinGeckoApiService,
                currency = mapper.mapCurrencyToCoinGeckoApiValue(settingsConfiguration.getCurrency()),
                order = mapper.mapOrderingToCoinGeckoApiValue(settingsConfiguration.getOrdering()),
                includeSparkline7dData = includeSparklineData,
                priceChangePercentageIntervals = "7d"
            )
        }.flow.map { pagingData ->
            pagingData.map { mapper.mapCoin(it) }
        }
    }

}