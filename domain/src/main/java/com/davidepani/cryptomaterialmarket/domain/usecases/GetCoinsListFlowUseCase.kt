package com.davidepani.cryptomaterialmarket.domain.usecases

import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.Coin
import com.davidepani.cryptomaterialmarket.domain.models.SettingsConfiguration
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetCoinsListFlowUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val settingsConfiguration: SettingsConfiguration
) {

    operator fun invoke(): Flow<List<Coin>> {
        return coinsRepository.getAllCoins()
    }

}