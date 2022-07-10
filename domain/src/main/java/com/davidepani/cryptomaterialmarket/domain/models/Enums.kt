package com.davidepani.cryptomaterialmarket.domain.models

enum class Ordering {
    MarketCapAsc,
    MarketCapDesc,
    PriceAsc,
    PriceDesc,
    PriceChangeAsc,
    PriceChangeDesc,
    NameAsc,
    NameDesc
}

enum class Currency {
    USD,
    EUR,
    BTC
}