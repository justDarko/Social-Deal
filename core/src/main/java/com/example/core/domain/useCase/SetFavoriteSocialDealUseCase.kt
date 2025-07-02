package com.example.core.domain.useCase

import com.example.core.domain.model.SocialDeal
import com.example.core.domain.repositories.SocialDealListRepository
import javax.inject.Inject

class SetFavoriteSocialDealUseCase @Inject constructor(
    private val socialDealListRepository: SocialDealListRepository
) : BaseUseCaseNoResult<SetFavoriteSocialDealUseCase.Params>() {

    override suspend operator fun invoke(params: Params) {
        val isFavorite = params.socialDeal.isFavorite
        val socialDeal = params.socialDeal.copy(
            isFavorite = !isFavorite
        )
        return socialDealListRepository.setFavoriteSocialDeal(
            socialDeal = socialDeal
        )
    }

    data class Params(
        val socialDeal: SocialDeal
    )
}