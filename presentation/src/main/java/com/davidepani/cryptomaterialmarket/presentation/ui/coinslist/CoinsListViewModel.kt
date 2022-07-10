package com.davidepani.cryptomaterialmarket.presentation.ui.coinslist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidepani.cryptomaterialmarket.domain.models.Result
import com.davidepani.cryptomaterialmarket.domain.usecases.GetCoinsListUseCase
import com.davidepani.cryptomaterialmarket.presentation.mappers.UiMapper
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListStateItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    private val getCoinsListUseCase: GetCoinsListUseCase,
    private val mapper: UiMapper
) : ViewModel() {

    val itemsList = mutableStateListOf<CoinsListStateItems>()

    var numLoadedPages: Int = 0
    var pageSize: Int = 10


    init {
        getNextPage()
    }

    fun getNextPage() {

        val numNextPage = numLoadedPages + 1


        itemsList.add(
            CoinsListStateItems.Loading(
                startIndex = (numLoadedPages * pageSize) + 1,
                endIndex = (numNextPage) * pageSize
            )
        )

        viewModelScope.launch {
            val result = getCoinsListUseCase(
                currency = "usd",
                numCoinsPerPage = pageSize,
                page = numNextPage
            )

            itemsList.removeLast() // Remove loading item

            when(result) {
                is Result.Success -> {
                    numLoadedPages++
                    itemsList.addAll(mapper.mapCoinUiItemsList(result.value))
                    itemsList.add(CoinsListStateItems.LoadMore)
                }
                is Result.Failure -> itemsList.add(CoinsListStateItems.Error(result.error.toString()))
            }

        }
    }


    fun onRetryButtonClick() {
        itemsList.removeLast()
        getNextPage()
    }

    fun onLoadMoreButtonClick() {
        itemsList.removeLast()
        getNextPage()
    }

}