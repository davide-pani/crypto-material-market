package com.davidepani.cryptomaterialmarket.presentation.ui.coinslist

import android.widget.Toast
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.davidepani.cryptomaterialmarket.presentation.models.CoinUiItem
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListState
import com.davidepani.cryptomaterialmarket.presentation.theme.CryptoMaterialMarketTheme
import com.davidepani.cryptomaterialmarket.presentation.theme.StocksDarkPrimaryText
import com.davidepani.cryptomaterialmarket.presentation.theme.StocksDarkSecondaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsListScreen(viewModel: CoinsListViewModel = viewModel()) {

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec,
        rememberTopAppBarScrollState()
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("Market") },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->

            val state = viewModel.uiState.observeAsState()

            state.value?.let {
                when(it) {
                    is CoinsListState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = StocksDarkSecondaryText)
                        }
                    }
                    is CoinsListState.Error -> {}
                    is CoinsListState.Success -> {

                        LazyColumn(
                            contentPadding = innerPadding,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(it.coinsList) { item ->
                                CoinItem(coinUiItem = item)
                            }
                        }

                    }

                }
            }

        }
    )

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CoinItem(coinUiItem: CoinUiItem) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .wrapContentHeight(),
        onClick = { Toast.makeText(context, "${coinUiItem.name} clicked", Toast.LENGTH_SHORT).show() },
        colors = CardDefaults.cardColors(
            contentColor = StocksDarkPrimaryText
        )
    ) {

        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = rememberAsyncImagePainter(coinUiItem.imageUrl),
                    contentDescription = null,
                    Modifier.size(40.dp)
                )
                Column(modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = coinUiItem.name, fontWeight = FontWeight.Medium)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Card(
                            shape = MaterialTheme.shapes.extraSmall,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        ) {
                            Text(
                                text = coinUiItem.marketCapRank,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 1.dp, bottom = 1.dp),
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            text = coinUiItem.symbol,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            fontWeight = FontWeight.Medium
                        )
                    }

                }
            }

            Column(modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(text = coinUiItem.price, fontWeight = FontWeight.Medium)

                if (!coinUiItem.priceChangePercentage7d.isNullOrBlank() && coinUiItem.trendColor != null ) {
                    Card(
                        shape = MaterialTheme.shapes.extraSmall,
                        colors = CardDefaults.cardColors(
                            containerColor = coinUiItem.trendColor,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.sizeIn(minWidth = 72.dp)
                    ) {
                        Text(
                            text = coinUiItem.priceChangePercentage7d,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 1.dp)
                                .align(Alignment.End),
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.End
                        )
                    }

                }


            }

        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoinsListScreenPreview() {
    CryptoMaterialMarketTheme {
        CoinsListScreen()
    }
}