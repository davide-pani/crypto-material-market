package com.davidepani.cryptomaterialmarket.domain.usecases

import com.davidepani.cryptomaterialmarket.domain.interfaces.TopCoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.CoinWithMarketData
import com.davidepani.cryptomaterialmarket.domain.models.NUM_TOP_COINS_TO_SHOW
import com.davidepani.cryptomaterialmarket.domain.models.SettingsConfiguration
import javax.inject.Inject


class RefreshTopCoinsUseCase @Inject constructor(
    private val topCoinsRepository: TopCoinsRepository,
    private val settingsConfiguration: SettingsConfiguration
) {

    suspend operator fun invoke(): Result<List<CoinWithMarketData>> {
        //val settings = settingsRepository.getSettingsFlow().last()
        val settings = settingsConfiguration

        return topCoinsRepository.refreshTopCoins(
            numCoins = NUM_TOP_COINS_TO_SHOW,
            currency = settings.currency,
            ordering = settings.ordering
        )
    }

}