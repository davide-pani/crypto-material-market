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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.davidepani.cryptomaterialmarket.presentation.R
import com.davidepani.cryptomaterialmarket.presentation.customcomposables.LineChart
import com.davidepani.cryptomaterialmarket.presentation.models.CoinUiItem
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListState
import com.davidepani.cryptomaterialmarket.presentation.theme.CryptoMaterialMarketTheme
import com.davidepani.cryptomaterialmarket.presentation.theme.StocksDarkPrimaryText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsListScreen(viewModel: CoinsListViewModel = viewModel()) {

    val context = LocalContext.current

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

            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                item {
                    PoweredByCoinGeckoItem()
                }

                items(viewModel.itemsList) { item ->

                    CoinItem(
                        item = item,
                        onCoinItemClick = { Toast.makeText(context, "${item.name} clicked", Toast.LENGTH_SHORT).show() }
                    )

                }

                item {
                    when(val item = viewModel.stateItem.value) {
                        is CoinsListState.LoadMore -> LoadMoreItem(onLoadMoreClick = { viewModel.onLoadMoreButtonClick() })
                        is CoinsListState.Loading -> LoadingItem(item = item)
                        is CoinsListState.Error -> ErrorItem(item = item, onRetryClick = { viewModel.onRetryButtonClick() })
                    }
                }

            }

        }
    )

}


@Composable
private fun PoweredByCoinGeckoItem() {

    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Powered by ",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall
        )
        Image(
            modifier = Modifier
                .requiredHeight(20.dp)
                .padding(top = 2.dp),
            painter = painterResource(id = R.drawable.ic_coingecko),
            contentDescription = null,
        )
    }

}

@Composable
private fun ErrorItem(
    item: CoinsListState.Error,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .defaultMinSize(minHeight = 48.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalArrangement =Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Error", color = MaterialTheme.colorScheme.onBackground)
        Text(text = item.message, color = MaterialTheme.colorScheme.onBackground)
        OutlinedButton(
            onClick = { onRetryClick.invoke() },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = StocksDarkPrimaryText
            )
        ) {
            Text(text = "Retry")
        }
    }
}

@Composable
private fun LoadMoreItem(
    onLoadMoreClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = { onLoadMoreClick.invoke() },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = StocksDarkPrimaryText
            )
        ) {
            Text(text = "Load more coins")
        }
    }
}

@Composable
private fun LoadingItem(item: CoinsListState.Loading) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .wrapContentHeight()
    ) {
        CircularProgressIndicator(color = StocksDarkPrimaryText)
        Text(
            text = "Loading coins...",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CoinItem(
    item: CoinUiItem,
    onCoinItemClick: (CoinUiItem) -> Unit
) {

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .wrapContentHeight(),
        onClick = { onCoinItemClick.invoke(item) },
        colors = CardDefaults.cardColors(
            contentColor = StocksDarkPrimaryText
        )
    ) {

        ConstraintLayout(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {

            val (iconImage, nameColumn, chart, priceColumn, maxWidthInvisiblePriceText) = createRefs()

            AsyncImage(
                model = item.imageUrl,
                modifier = Modifier
                    .size(35.dp)
                    .clip(shape = MaterialTheme.shapes.medium)
                    .constrainAs(iconImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
                contentDescription = null,
                alignment = Alignment.Center,
                filterQuality = FilterQuality.None
            )

            Column(
                modifier = Modifier.constrainAs(nameColumn) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(iconImage.end, margin = 8.dp)
                    end.linkTo(chart.start, margin = 8.dp)
                    width = Dimension.fillToConstraints
                },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(
                        shape = MaterialTheme.shapes.extraSmall,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text(
                            text = item.marketCapRank,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 1.dp, bottom = 1.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = item.symbol,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontWeight = FontWeight.Medium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }

            if (!item.sparkline7dData.isNullOrEmpty() && item.trendColor != null) {
                // Invisible text with max price size to determine the max possible size of this column
                Text(
                    text = "$100,000.00",
                    modifier = Modifier
                        .constrainAs(maxWidthInvisiblePriceText) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .alpha(0f)
                )

                LineChart(
                    modifier = Modifier
                        .constrainAs(chart) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(maxWidthInvisiblePriceText.start, margin = 4.dp)
                        }
                        .size(width = 50.dp, height = 30.dp),
                    data = item.sparkline7dData,
                    graphColor = item.trendColor
                )
            }

            Column(
                modifier = Modifier
                    .constrainAs(priceColumn) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {

                Text(
                    text = item.price,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )

                if (!item.priceChangePercentage7d.isNullOrBlank() && item.trendColor != null ) {
                    Card(
                        modifier = Modifier.sizeIn(minWidth = 72.dp),
                        shape = MaterialTheme.shapes.extraSmall,
                        colors = CardDefaults.cardColors(
                            containerColor = item.trendColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = item.priceChangePercentage7d,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 1.dp)
                                .align(Alignment.End),
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.End,
                            maxLines = 1
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