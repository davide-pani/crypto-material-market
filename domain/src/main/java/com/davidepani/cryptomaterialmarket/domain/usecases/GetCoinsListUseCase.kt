package com.davidepani.cryptomaterialmarket.domain.usecases

import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.Coin
import com.davidepani.cryptomaterialmarket.domain.models.Result
import com.davidepani.cryptomaterialmarket.domain.models.SettingsConfiguration
import javax.inject.Inject


class GetCoinsListUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val settingsConfiguration: SettingsConfiguration
) {

    suspend operator fun invoke(page: Int): Result<List<Coin>> {
        return coinsRepository.retrieveCoinsList(
            currency = settingsConfiguration.getCurrency(),
            numCoinsPerPage = settingsConfiguration.coinsListPageSize,
            page = page,
            ordering = settingsConfiguration.getOrdering(),
            includeSparklineData = true
        )
    }

}