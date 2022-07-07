package com.davidepani.cryptomaterialmarket.presentation.mappers

import com.davidepani.cryptomaterialmarket.domain.entities.Coin
import com.davidepani.cryptomaterialmarket.presentation.models.CoinUi
import com.davidepani.cryptomaterialmarket.presentation.theme.NegativeTrend
import com.davidepani.cryptomaterialmarket.presentation.theme.PositiveTrend
import com.davidepani.kotlinextensions.formatToCurrency
import com.davidepani.kotlinextensions.formatToPercentage
import com.davidepani.kotlinextensions.utils.currencyformatter.CurrencyFormatter
import com.davidepani.kotlinextensions.utils.numberformatter.NumberFormatter
import javax.inject.Inject

class UiMapper @Inject constructor(
    private val currencyFormatter: CurrencyFormatter,
    private val numberFormatter: NumberFormatter
) {

    fun mapCoinUi(coin: Coin): CoinUi {
        return CoinUi(
            name = coin.name,
            symbol = coin.symbol,
            imageUrl = coin.image,
            price = coin.price.formatToCurrency(currencyFormatter),
            marketCapRank = coin.marketCapRank.toString(),
            priceChangePercentage7d = coin.priceChangePercentage7d?.formatToPercentage(numberFormatter),
            trendColor = coin.priceChangePercentage7d?.let {
                if (it >= 0) PositiveTrend else NegativeTrend
            },
            sparkline7dData = coin.sparkline7dData
        )
    }

}