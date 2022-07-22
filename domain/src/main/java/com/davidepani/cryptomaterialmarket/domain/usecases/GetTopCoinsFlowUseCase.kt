package com.davidepani.cryptomaterialmarket.domain.usecases

import com.davidepani.cryptomaterialmarket.domain.interfaces.TopCoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.TopCoinData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetTopCoinsFlowUseCase @Inject constructor(
    private val topCoinsRepository: TopCoinsRepository,
) {

    operator fun invoke(): Flow<TopCoinData> {
        return topCoinsRepository.getTopCoinsFlow()
    }

}