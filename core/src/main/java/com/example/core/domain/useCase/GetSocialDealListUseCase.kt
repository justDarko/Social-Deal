package com.example.core.domain.useCase

import com.example.core.data.CustomResult
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.repositories.SocialDealListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSocialDealListUseCase @Inject constructor(
    private val socialDealListRepository: SocialDealListRepository
) : BaseUseCaseFlowNoParams<List<SocialDeal>>() {

    override fun invoke(): Flow<CustomResult<List<SocialDeal>>> {
        return socialDealListRepository.getSocialDealList()
    }
}