package com.davidepani.cryptomaterialmarket.domain.usecases

import com.davidepani.cryptomaterialmarket.domain.interfaces.TopCoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.CoinWithMarketData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetTopCoinsFlowUseCase @Inject constructor(
    private val topCoinsRepository: TopCoinsRepository,
) {

    operator fun invoke(): Flow<List<CoinWithMarketData>> {
        return topCoinsRepository.getTopCoinsFlow()
    }

}