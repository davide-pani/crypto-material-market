package com.davidepani.cryptomaterialmarket.presentation.ui.coinslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidepani.cryptomaterialmarket.domain.entities.Result
import com.davidepani.cryptomaterialmarket.domain.usecases.GetCoinsListUseCase
import com.davidepani.cryptomaterialmarket.presentation.mappers.UiMapper
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    private val getCoinsListUseCase: GetCoinsListUseCase,
    private val mapper: UiMapper
) : ViewModel() {

    private val _uiState = MutableLiveData<CoinsListState>()
    val uiState: LiveData<CoinsListState> = _uiState


    init {
        getCoin()
    }

    private fun getCoin() {
        _uiState.value = CoinsListState.Loading

        viewModelScope.launch {
            val result = getCoinsListUseCase()

            _uiState.value = when(result) {
                is Result.Success -> {
                    val mappedList = mapper.mapCoinUiItemsList(result.value)
                    CoinsListState.Success(mappedList)
                }
                is Result.Failure -> CoinsListState.Error(result.error.toString())
            }
        }
    }

    fun onRetryButtonClick() {
        getCoin()
    }

}