package com.davidepani.cryptomaterialmarket.data.mappers

import com.davidepani.cryptomaterialmarket.data.models.CoinApiResponse
import com.davidepani.cryptomaterialmarket.domain.models.Coin
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import javax.inject.Inject

class DataMapper @Inject constructor() {

    fun mapCoin(coinResponse: CoinApiResponse): Coin {
        return Coin(
            id = coinResponse.id,
            name = coinResponse.name,
            symbol = coinResponse.symbol,
            price = coinResponse.currentPrice,
            marketCap = coinResponse.marketCap,
            image = coinResponse.image,
            marketCapRank = coinResponse.marketCapRank,
            priceChangePercentage = coinResponse.priceChangePercentage7dInCurrency,
            sparklineData = coinResponse.sparklineIn7d?.price
        )
    }

    fun mapCoinsList(coinsListResponse: List<CoinApiResponse>): List<Coin> {
        return coinsListResponse.map { mapCoin(it) }
    }

    fun mapCurrencyToCoinGeckoApiValue(currency: Currency): String {
        return when(currency) {
            Currency.USD -> "usd"
            Currency.EUR -> "eur"
            Currency.BTC -> "btc"
        }
    }

    fun mapOrderingToCoinGeckoApiValue(ordering: Ordering): String {
        return when(ordering) {
            Ordering.MarketCapDesc -> "market_cap_desc"
            else -> ""
        }
    }
}