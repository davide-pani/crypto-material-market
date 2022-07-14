package com.davidepani.cryptomaterialmarket.domain.usecases

import androidx.paging.PagingData
import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.Coin
import com.davidepani.cryptomaterialmarket.domain.models.SettingsConfiguration
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCoinsListWithPagingUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository,
    private val settingsConfiguration: SettingsConfiguration
) {

    operator fun invoke(
        pageSize: Int,
        initialPageSize: Int,
        prefetchDistance: Int
    ): Flow<PagingData<Coin>> = coinsRepository.retrieveCoinsListWithPaging(
        settingsConfiguration = settingsConfiguration,
        pageSize = pageSize,
        initialPageSize = initialPageSize,
        prefetchDistance = prefetchDistance,
        includeSparklineData = true
    )

}