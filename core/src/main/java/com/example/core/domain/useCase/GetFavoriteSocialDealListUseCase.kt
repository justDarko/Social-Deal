package com.example.core.domain.useCase

import com.example.core.data.CustomResult
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.repositories.FavoriteSocialDealListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteSocialDealListUseCase @Inject constructor(
    private val favoriteSocialDealListRepository: FavoriteSocialDealListRepository
) : BaseUseCaseFlowNoParams<List<SocialDeal>>() {

    override fun invoke(): Flow<CustomResult<List<SocialDeal>>> {
        return favoriteSocialDealListRepository.getFavoriteSocialDealList()
    }
}