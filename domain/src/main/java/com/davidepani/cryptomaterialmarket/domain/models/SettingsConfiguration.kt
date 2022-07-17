package com.davidepani.cryptomaterialmarket.domain.models

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsConfiguration @Inject constructor(
    internal var currency: Currency,
    internal var ordering: Ordering,
    val coinsListPageSize: Int
) {

    fun getCurrency() = currency
    fun getOrdering() = ordering

}