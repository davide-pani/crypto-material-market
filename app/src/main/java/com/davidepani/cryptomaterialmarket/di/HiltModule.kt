package com.davidepani.cryptomaterialmarket.di

import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import com.davidepani.cryptomaterialmarket.domain.models.SettingsConfiguration
import com.davidepani.kotlinextensions.utils.currencyformatter.CurrencyFormatter
import com.davidepani.kotlinextensions.utils.currencyformatter.LocalizedCurrencyFormatter
import com.davidepani.kotlinextensions.utils.numberformatter.LocalizedNumberFormatter
import com.davidepani.kotlinextensions.utils.numberformatter.NumberFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideCurrencyFormatter(): CurrencyFormatter {
        return LocalizedCurrencyFormatter(
            minimumFractionDigits = 2,
            maximumFractionDigits = 6
        )
    }

    @Provides
    @Singleton
    fun provideNumberFormatter(): NumberFormatter {
        return LocalizedNumberFormatter(
            locale = Locale.getDefault(),
            showAlwaysSign = true,
            maximumFractionDigits = 2,
            minimumFractionDigits = 2,
            divideValueBy100 = true
        )
    }

    @Provides
    @Singleton
    fun provideDateTimeFormatter(): DateTimeFormatter {
        return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
    }

    @Provides
    @Singleton
    fun provideSettingsConfiguration(): SettingsConfiguration {
        return SettingsConfiguration(
            currency = Currency.USD,
            ordering = Ordering.MarketCapDesc,
            coinsListPageSize = 100
        )
    }

    @Provides
    @Singleton
    fun providesDefaultDispatcher(): Dispatchers = Dispatchers

}