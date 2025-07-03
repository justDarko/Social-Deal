package com.example.core.domain.useCase

import com.example.core.data.CustomResult
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.repositories.SocialDealDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSocialDealDetailsUseCase @Inject constructor(
    private val socialDealDetailsRepository: SocialDealDetailsRepository
) : BaseUseCaseFlow<GetSocialDealDetailsUseCase.Params, SocialDeal>() {

    override fun invoke(params: Params): Flow<CustomResult<SocialDeal>> {
        return socialDealDetailsRepository.getSocialDealDetails(id = params.id)
    }

    data class Params(
        val id: String
    )
}