package com.davidepani.cryptomaterialmarket.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.davidepani.cryptomaterialmarket.data.api.CoinGeckoApiService
import com.davidepani.cryptomaterialmarket.data.models.CoinApiResponse

class CoinGeckoCoinsPagingSource(
    private val coinGeckoApiService: CoinGeckoApiService,
    private val currency: String,
    private val order: String,
    private val includeSparkline7dData: Boolean,
    private val priceChangePercentageIntervals: String = ""
) : PagingSource<Int, CoinApiResponse>() {

    override fun getRefreshKey(state: PagingState<Int, CoinApiResponse>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinApiResponse> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val response = coinGeckoApiService.getCoinsMarkets(
                page = page,
                numCoinsPerPage = pageSize,
                currency = currency,
                order = order,
                includeSparkline7dData = includeSparkline7dData,
                priceChangePercentageIntervals = priceChangePercentageIntervals
            )

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.size == pageSize) page + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

}