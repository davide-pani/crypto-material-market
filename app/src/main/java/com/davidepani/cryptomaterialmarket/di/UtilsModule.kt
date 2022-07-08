package com.davidepani.cryptomaterialmarket.di

import com.davidepani.kotlinextensions.utils.currencyformatter.CurrencyFormatter
import com.davidepani.kotlinextensions.utils.currencyformatter.LocalizedCurrencyFormatter
import com.davidepani.kotlinextensions.utils.numberformatter.LocalizedNumberFormatter
import com.davidepani.kotlinextensions.utils.numberformatter.NumberFormatter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

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

}