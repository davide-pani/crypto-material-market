package com.davidepani.cryptomaterialmarket.presentation.ui.coindetail

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import coil.compose.AsyncImage
import com.davidepani.cryptomaterialmarket.presentation.R
import com.davidepani.cryptomaterialmarket.presentation.models.COIN_DETAIL_PARAMETER
import com.davidepani.cryptomaterialmarket.presentation.models.CoinUiItem
import com.davidepani.cryptomaterialmarket.presentation.models.Screen
import com.davidepani.cryptomaterialmarket.presentation.theme.StocksDarkPrimaryText
import com.davidepani.cryptomaterialmarket.presentation.theme.StocksDarkSecondaryText
import com.mxalbert.sharedelements.SharedElement
import dev.olshevski.navigation.reimagined.NavController
import dev.olshevski.navigation.reimagined.hilt.hiltViewModel
import dev.olshevski.navigation.reimagined.pop


// Key from shared elements transition
private const val COIN_DETAIL_SCREEN_KEY = "COIN_DETAIL_SCREEN_KEY"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreen(
    coinId: CoinUiItem,
    navController: NavController<Screen>,
    viewModel: CoinDetailViewModel = hiltViewModel(
        defaultArguments = bundleOf(COIN_DETAIL_PARAMETER to coinId)
    )
) {

    val context = LocalContext.current

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()

    /*
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec,
        rememberTopAppBarScrollState()
    )

     */

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        //modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.pop() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back_ios),
                            contentDescription = "Localized description",
                            modifier = Modifier.padding(start = 12.dp)
                        )
                    }
                },
                //scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding).padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight().background(MaterialTheme.colorScheme.background),
                verticalAlignment = Alignment.CenterVertically
            ) {

                viewModel.coinId?.let {
                    SharedElement(key = it.imageUrl, screenKey = COIN_DETAIL_SCREEN_KEY) {
                        AsyncImage(
                            model = it.imageUrl,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(shape = MaterialTheme.shapes.large),
                            contentDescription = null,
                            filterQuality = FilterQuality.High
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Column() {
                        Text(
                            text = viewModel.coinId?.name ?: "",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Medium,
                            color = StocksDarkPrimaryText
                        )
                        Text(
                            text = viewModel.coinId?.symbol ?: "",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = StocksDarkSecondaryText
                        )
                    }
                }
            }



        }

    }

}
