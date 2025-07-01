package com.example.core.domain.useCase

import com.example.core.data.CustomResult
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.repositories.SocialDealListRepository
import javax.inject.Inject

class GetSocialDealListUseCase @Inject constructor(
    private val socialDealListRepository: SocialDealListRepository
) : BaseUseCase<Unit, List<SocialDeal>>() {

    override suspend fun invoke(params: Unit): CustomResult<List<SocialDeal>> {
        return socialDealListRepository.getSocialDealList()
    }
}