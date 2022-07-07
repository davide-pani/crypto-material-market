package com.davidepani.cryptomaterialmarket.presentation.ui.coinslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidepani.cryptomaterialmarket.domain.usecases.GetCoinsListUseCase
import com.davidepani.cryptomaterialmarket.presentation.mappers.UiMapper
import com.davidepani.cryptomaterialmarket.presentation.models.CoinUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    private val getCoinsListUseCase: GetCoinsListUseCase,
    private val mapper: UiMapper
) : ViewModel() {

    private val _uiState = MutableLiveData<CoinUiState>()
    val uiState: LiveData<CoinUiState> = _uiState


    init {
        getCoin()
    }

    fun getCoin() {
        _uiState.value = CoinUiState.Loading

        viewModelScope.launch {
        }
    }

    fun onRetryButtonClick() {
        getCoin()
    }

}