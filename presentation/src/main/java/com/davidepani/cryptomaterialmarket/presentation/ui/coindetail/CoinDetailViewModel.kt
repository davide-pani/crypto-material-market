package com.davidepani.cryptomaterialmarket.presentation.ui.coindetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.davidepani.cryptomaterialmarket.domain.usecases.GetCoinsListWithPagingUseCase
import com.davidepani.cryptomaterialmarket.domain.usecases.UpdateSettingsUseCase
import com.davidepani.cryptomaterialmarket.presentation.mappers.UiMapper
import com.davidepani.cryptomaterialmarket.presentation.models.COIN_DETAIL_PARAMETER
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    getCoinsListWithPagingUseCase: GetCoinsListWithPagingUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    private val mapper: UiMapper,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val coinId: String? get() = savedStateHandle[COIN_DETAIL_PARAMETER]

    val state by mutableStateOf(coinId)

}