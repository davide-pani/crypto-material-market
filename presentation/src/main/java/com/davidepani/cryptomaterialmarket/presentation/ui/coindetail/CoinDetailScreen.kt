package com.davidepani.cryptomaterialmarket.presentation.ui.coindetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import coil.compose.AsyncImage
import com.davidepani.cryptomaterialmarket.presentation.models.COIN_DETAIL_PARAMETER
import com.mxalbert.sharedelements.SharedElement
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel


// Key from shared elements transition
private const val COIN_DETAIL_SCREEN_KEY = "COIN_DETAIL_SCREEN_KEY"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreen(
    coinId: String,
    viewModel: CoinDetailViewModel = hiltViewModel(
        defaultArguments = bundleOf(COIN_DETAIL_PARAMETER to coinId)
    )
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        viewModel.state?.let {
            SharedElement(key = it, screenKey = COIN_DETAIL_SCREEN_KEY) {
                AsyncImage(
                    model = it,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(shape = MaterialTheme.shapes.medium),
                    contentDescription = null,
                    filterQuality = FilterQuality.None
                )
            }
        }
    }

}
