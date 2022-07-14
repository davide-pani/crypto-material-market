package com.davidepani.cryptomaterialmarket.presentation.ui.coinslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import com.davidepani.cryptomaterialmarket.domain.usecases.GetCoinsListWithPagingUseCase
import com.davidepani.cryptomaterialmarket.domain.usecases.UpdateSettingsUseCase
import com.davidepani.cryptomaterialmarket.presentation.mappers.UiMapper
import com.davidepani.cryptomaterialmarket.presentation.models.CoinUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    getCoinsListWithPagingUseCase: GetCoinsListWithPagingUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    private val mapper: UiMapper,
) : ViewModel() {

    private val pageSize: Int = 50

    val pagedCoinItemsFlow: Flow<PagingData<CoinUiItem>> = getCoinsListWithPagingUseCase(
        pageSize = pageSize,
        initialPageSize = pageSize,
        prefetchDistance = 20
    ).map { pagingData ->
        pagingData.map { mapper.mapCoinUiItem(it) }
    }.cachedIn(viewModelScope)

    fun updateSettings() {
        updateSettingsUseCase(Currency.BTC, Ordering.MarketCapDesc)
    }

}