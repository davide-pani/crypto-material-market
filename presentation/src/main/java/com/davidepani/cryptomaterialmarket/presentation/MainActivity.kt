package com.davidepani.cryptomaterialmarket.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.davidepani.cryptomaterialmarket.presentation.models.Screen
import com.davidepani.cryptomaterialmarket.presentation.theme.CryptoMaterialMarketTheme
import com.davidepani.cryptomaterialmarket.presentation.ui.coindetail.CoinDetailScreen
import com.davidepani.cryptomaterialmarket.presentation.ui.coinslist.CoinsListScreen
import com.mxalbert.sharedelements.SharedElementsRoot
import dagger.hilt.android.AndroidEntryPoint
import dev.olshevski.navigation.reimagined.AnimatedNavHost
import dev.olshevski.navigation.reimagined.AnimatedNavHostTransitionSpec
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.rememberNavController

@AndroidEntryPoint
@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    private val mainNavHostTransitionSpec =
        AnimatedNavHostTransitionSpec<Screen> { _, _, _ ->
            fadeIn(tween(0)) with fadeOut(tween(0))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoMaterialMarketTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SharedElementsRoot {
                        val navController = rememberNavController<Screen>(
                            startDestination = Screen.CoinsList
                        )
                        NavBackHandler(navController)
                        AnimatedNavHost(
                            controller = navController,
                            transitionSpec = mainNavHostTransitionSpec
                        ) { route ->
                            when(route) {
                                is Screen.CoinsList -> { CoinsListScreen(navController = navController) }
                                is Screen.CoinDetail -> { CoinDetailScreen(coinId = route.coinId) }
                            }
                        }
                    }

                }
            }
        }
    }
}