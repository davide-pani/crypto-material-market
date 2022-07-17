package com.davidepani.cryptomaterialmarket.presentation.ui.coindetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.davidepani.cryptomaterialmarket.domain.usecases.UpdateSettingsUseCase
import com.davidepani.cryptomaterialmarket.presentation.mappers.UiMapper
import com.davidepani.cryptomaterialmarket.presentation.models.COIN_DETAIL_PARAMETER
import com.davidepani.cryptomaterialmarket.presentation.models.CoinUiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    private val mapper: UiMapper,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val coinId: CoinUiItem? get() = savedStateHandle[COIN_DETAIL_PARAMETER]

}