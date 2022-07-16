package com.davidepani.cryptomaterialmarket.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.davidepani.cryptomaterialmarket.presentation.models.Screen
import com.davidepani.cryptomaterialmarket.presentation.theme.CryptoMaterialMarketTheme
import com.davidepani.cryptomaterialmarket.presentation.ui.coindetail.CoinDetailScreen
import com.davidepani.cryptomaterialmarket.presentation.ui.coinslist.CoinsListScreen
import dagger.hilt.android.AndroidEntryPoint
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.rememberNavController

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoMaterialMarketTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController<Screen>(
                        startDestination = Screen.CoinsList
                    )
                    NavBackHandler(navController)
                    NavHost(navController) { route ->
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