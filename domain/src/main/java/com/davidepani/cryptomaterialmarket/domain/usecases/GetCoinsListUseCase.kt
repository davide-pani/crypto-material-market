package com.davidepani.cryptomaterialmarket.domain.usecases

import com.davidepani.cryptomaterialmarket.domain.entities.Coin
import com.davidepani.cryptomaterialmarket.domain.entities.Result
import com.davidepani.cryptomaterialmarket.domain.interfaces.CoinsRepository
import javax.inject.Inject


class GetCoinsListUseCase @Inject constructor(
    private val coinsRepository: CoinsRepository
) {

    suspend operator fun invoke(
        currency: String = "usd",
        numCoinsPerPage: Int = 100,
        page: Int = 1
    ): Result<List<Coin>> {
        return coinsRepository.retrieveCoinsList(
            currency = currency,
            numCoinsPerPage = numCoinsPerPage,
            page = page,
            includeSparkline7dData = true
        )
    }

}