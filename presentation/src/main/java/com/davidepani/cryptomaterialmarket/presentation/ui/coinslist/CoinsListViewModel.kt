package com.davidepani.cryptomaterialmarket.presentation.ui.coinslist

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Result
import com.davidepani.cryptomaterialmarket.domain.usecases.GetCoinsListUseCase
import com.davidepani.cryptomaterialmarket.presentation.mappers.UiMapper
import com.davidepani.cryptomaterialmarket.presentation.models.CoinUiItem
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    private val getCoinsListUseCase: GetCoinsListUseCase,
    private val mapper: UiMapper
) : ViewModel() {

    val itemsList = mutableStateListOf<CoinUiItem>()
    val stateItem = mutableStateOf<CoinsListState>(CoinsListState.LoadMore)

    var numLoadedPages: Int = 0
    var pageSize: Int = 10


    init {
        getNextPage()
    }

    fun getNextPage() {

        val numNextPage = numLoadedPages + 1

        stateItem.value = CoinsListState.Loading(
            startIndex = (numLoadedPages * pageSize) + 1,
            endIndex = (numNextPage) * pageSize
        )

        viewModelScope.launch {
            val result = getCoinsListUseCase(
                currency = Currency.USD,
                numCoinsPerPage = pageSize,
                page = numNextPage
            )

            when(result) {
                is Result.Success -> {
                    numLoadedPages++
                    itemsList.addAll(mapper.mapCoinUiItemsList(result.value))
                    stateItem.value = CoinsListState.LoadMore
                }
                is Result.Failure -> stateItem.value = CoinsListState.Error(result.error.toString())
            }

        }
    }


    fun onRetryButtonClick() {
        getNextPage()
    }

    fun onLoadMoreButtonClick() {
        getNextPage()
    }

}