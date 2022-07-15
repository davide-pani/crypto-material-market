package com.davidepani.cryptomaterialmarket.presentation.ui.coinslist

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.davidepani.cryptomaterialmarket.presentation.R
import com.davidepani.cryptomaterialmarket.presentation.customcomposables.LineChart
import com.davidepani.cryptomaterialmarket.presentation.models.CoinUiItem
import com.davidepani.cryptomaterialmarket.presentation.models.CoinsListState
import com.davidepani.cryptomaterialmarket.presentation.theme.CryptoMaterialMarketTheme
import com.davidepani.cryptomaterialmarket.presentation.theme.StocksDarkPrimaryText
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsListScreen(viewModel: CoinsListViewModel = viewModel()) {

    val context = LocalContext.current

    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec,
        rememberTopAppBarScrollState()
    )

    val coinsListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isButtonVisible = remember {
        derivedStateOf {
            coinsListState.firstVisibleItemIndex >= 5
        }
    }

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
        }
    ) { innerPadding ->

        val coinItems = viewModel.pagedCoinItemsFlow.collectAsLazyPagingItems()

        LazyColumn(
            state = coinsListState,
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item {
                PoweredByCoinGeckoItem {
                    viewModel.updateSettings()
                    coinItems.refresh()
                }
            }

            items(coinItems, key = { it.marketCapRank }) { item ->

                item?.let {
                    CoinItem(
                        item = it,
                        onCoinItemClick = { Toast.makeText(context, "${item.name} clicked", Toast.LENGTH_SHORT).show() }
                    )
                }

            }

            item {
                with(coinItems.loadState) {
                    when {
                        refresh is LoadState.Loading -> LoadingItem(modifier = Modifier.fillParentMaxHeight())
                        append is LoadState.Loading -> LoadingItem(modifier = Modifier.wrapContentHeight())
                        refresh is LoadState.Error -> ErrorItem(
                            modifier = Modifier.fillParentMaxHeight(),
                            item = CoinsListState.Error((refresh as LoadState.Error).error.toString())) {
                            coinItems.retry()
                        }
                        append is LoadState.Error -> ErrorItem(
                            modifier = Modifier.wrapContentHeight(),
                            item = CoinsListState.Error((append as LoadState.Error).error.toString())) {
                            coinItems.retry()
                        }
                    }
                }

            }

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.BottomCenter
        ) {
            AnimatedVisibility(
                visible = isButtonVisible.value,
                //enter = fadeIn(),
                //exit = fadeOut()
            ) {
                SmallFloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            coinsListState.animateScrollToItem(0)
                        }
                    },
                    modifier = Modifier.padding(8.dp).alpha(.8f)
                ) {
                    Icon(painterResource(id = R.drawable.ic_double_up_arrow), contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
                    //Text(text = "Back to top", color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(horizontal = 16.dp))
                }
            }

        }

    }

}


@Composable
private fun PoweredByCoinGeckoItem(onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick.invoke() },
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
    modifier: Modifier,
    item: CoinsListState.Error,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = item.message, color = MaterialTheme.colorScheme.onBackground, textAlign = TextAlign.Center)
        OutlinedButton(
            onClick = { onRetryClick.invoke() },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Text(text = "Retry")
        }
    }
}

@Composable
private fun LoadingItem(modifier: Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
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
        ),
        shape = MaterialTheme.shapes.medium
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

            if (!item.sparklineData.isNullOrEmpty() && item.trendColor != null) {
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
                    data = item.sparklineData,
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

                if (!item.priceChangePercentage.isNullOrBlank() && item.trendColor != null ) {
                    Card(
                        modifier = Modifier.sizeIn(minWidth = 72.dp),
                        shape = MaterialTheme.shapes.small,
                        colors = CardDefaults.cardColors(
                            containerColor = item.trendColor,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = item.priceChangePercentage,
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