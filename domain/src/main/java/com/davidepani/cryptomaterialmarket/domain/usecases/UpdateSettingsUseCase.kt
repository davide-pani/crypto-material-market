package com.davidepani.cryptomaterialmarket.domain.usecases

import com.davidepani.cryptomaterialmarket.domain.models.Currency
import com.davidepani.cryptomaterialmarket.domain.models.Ordering
import com.davidepani.cryptomaterialmarket.domain.models.SettingsConfiguration
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val settingsConfiguration: SettingsConfiguration
) {

    operator fun invoke(
        currency: Currency,
        ordering: Ordering
    ) {
        settingsConfiguration.apply {
            this.currency = currency
            this.ordering = ordering
        }

    }

}