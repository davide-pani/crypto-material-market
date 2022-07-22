package com.davidepani.cryptomaterialmarket.domain.usecases

import com.davidepani.cryptomaterialmarket.domain.interfaces.TopCoinsRepository
import com.davidepani.cryptomaterialmarket.domain.models.TOP_COINS_MINUTES_REFRESH_PERIOD
import com.davidepani.cryptomaterialmarket.domain.models.TopCoinData
import com.davidepani.kotlinextensions.minutesBetween
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import javax.inject.Inject


class GetTopCoinsFlowUseCase @Inject constructor(
    private val topCoinsRepository: TopCoinsRepository,
    private val refreshTopCoinsUseCase: RefreshTopCoinsUseCase
) {

    operator fun invoke(): Flow<TopCoinData> {
        return topCoinsRepository.getTopCoinsFlow().onEach {
            if (shouldRefresh(it)) {
                refreshTopCoinsUseCase()
            }
        }
    }

    private fun shouldRefresh(topCoinData: TopCoinData): Boolean {
        val minutesFromLastUpdate = topCoinData.lastUpdate.minutesBetween(LocalDateTime.now())
        return minutesFromLastUpdate > TOP_COINS_MINUTES_REFRESH_PERIOD
    }

}