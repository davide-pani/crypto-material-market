package com.davidepani.cryptomaterialmarket.data.mappers

import com.davidepani.cryptomaterialmarket.data.api.coingecko.models.CoinGeckoMarketsDto
import com.davidepani.cryptomaterialmarket.domain.models.CoinMarketData
import com.davidepani.cryptomaterialmarket.domain.models.CoinWithMarketData
import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

class CoinGeckoDataMapper @Inject constructor() {

    private fun mapCoinWithMarketData(coinResponse: CoinGeckoMarketsDto): CoinWithMarketData {
        return CoinWithMarketData(
            id = coinResponse.id,
            name = coinResponse.name,
            symbol = coinResponse.symbol,
            image = coinResponse.image,
            rank = coinResponse.marketCapRank,
            lastUpdate = Instant.parse(coinResponse.lastUpdated).atZone(ZoneId.systemDefault()).toLocalDateTime(),
            marketData = CoinMarketData(
                price = coinResponse.currentPrice,
                marketCap = coinResponse.marketCap,
                priceChangePercentage = coinResponse.priceChangePercentage7dInCurrency!!,
                sparklineData = coinResponse.sparklineIn7d?.price?.filterIndexed { index, _ ->
                    index % 5 == 0
                },
            )
        )
    }

    fun mapCoinsWithMarketDataList(coinsListResponse: List<CoinGeckoMarketsDto>): List<CoinWithMarketData> {
        return coinsListResponse.map { mapCoinWithMarketData(it) }
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