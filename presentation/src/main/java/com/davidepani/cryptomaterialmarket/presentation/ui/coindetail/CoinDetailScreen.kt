package com.davidepani.cryptomaterialmarket.presentation.ui.coindetail

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.core.os.bundleOf
import com.davidepani.cryptomaterialmarket.presentation.models.COIN_DETAIL_PARAMETER
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreen(
    coinId: String,
    viewModel: CoinDetailViewModel = hiltViewModel(
        defaultArguments = bundleOf(COIN_DETAIL_PARAMETER to coinId)
    )
) {

}
