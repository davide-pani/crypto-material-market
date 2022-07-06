package com.davidepani.cryptomaterialmarket.domain.usecases

import com.davidepani.cryptomaterialmarket.domain.entities.Coin
import com.davidepani.cryptomaterialmarket.domain.entities.Result
import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import javax.inject.Inject


class GetCoinsListUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
) {

    suspend operator fun invoke(): Result<List<Coin>> {
        return coinsRepository.retrieveCoinsList()
    }

}