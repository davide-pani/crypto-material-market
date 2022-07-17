package com.davidepani.cryptomaterialmarket.presentation.mappers

import android.os.Build
import com.davidepani.cryptomaterialmarket.domain.models.Coin
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.SettingsConfiguration
import com.davidepani.cryptomaterialmarket.presentation.models.CoinUiItem
import com.davidepani.cryptomaterialmarket.presentation.models.DataPoint
import com.davidepani.cryptomaterialmarket.presentation.theme.NegativeTrend
import com.davidepani.cryptomaterialmarket.presentation.theme.PositiveTrend
import com.davidepani.kotlinextensions.formatToCurrency
import com.davidepani.kotlinextensions.formatToPercentage
import com.davidepani.kotlinextensions.utils.currencyformatter.CurrencyFormatter
import com.davidepani.kotlinextensions.utils.numberformatter.NumberFormatter
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class UiMapper @Inject constructor(
    private val currencyFormatter: CurrencyFormatter,
    private val numberFormatter: NumberFormatter,
    private val settingsConfiguration: SettingsConfiguration
) {

    fun mapCoinUiItem(coin: Coin): CoinUiItem {
        return CoinUiItem(
            id = coin.id,
            name = coin.name,
            symbol = coin.symbol.uppercase(),
            imageUrl = coin.image,
            price = coin.price.formatToCurrency(
                currencyFormatter,
                customCurrencySymbol = when(settingsConfiguration.getCurrency()) {
                    Currency.USD -> "$"
                    Currency.EUR -> "€"
                    Currency.BTC -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) "₿" else "B"
                }
            ),
            marketCapRank = coin.marketCapRank.toString(),
            priceChangePercentage = coin.priceChangePercentage?.formatToPercentage(
                numberFormatter
            ),
            trendColor = coin.priceChangePercentage?.let {
                if (it >= 0) PositiveTrend else NegativeTrend
            },
            sparklineData = coin.sparklineData?.mapIndexed { index, d ->
                if (index % 3 == 0) {
                    DataPoint(x = index.toDouble(), y = d, label = null)
                } else null
            }?.filterNotNull()
        )
    }

    fun mapCoinUiItemsList(coinsList: List<Coin>): List<CoinUiItem> {
        return coinsList.map { mapCoinUiItem(it) }
    }

    fun mapErrorToUiMessage(error: Throwable): String {
        return when(error) {
            is UnknownHostException,
            is SocketTimeoutException,
            is SocketException,
            is IOException -> {
                "You appear to be offline. \n" +
                "Please, check your internet connection and retry."
            }
            else -> error.toString()
        }
    }

}