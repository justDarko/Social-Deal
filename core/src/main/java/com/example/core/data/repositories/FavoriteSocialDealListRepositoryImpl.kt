package com.example.core.data.repositories

import com.example.core.data.CustomResult
import com.example.core.data.local.db.SocialDealsDao
import com.example.core.data.toSocialDeal
import com.example.core.domain.model.SocialDeal
import com.example.core.domain.repositories.FavoriteSocialDealListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteSocialDealListRepositoryImpl @Inject constructor(
    private val socialDealsDao: SocialDealsDao
) : FavoriteSocialDealListRepository {

    override fun getFavoriteSocialDealList(): Flow<CustomResult<List<SocialDeal>>> = flow {
        try {
            socialDealsDao.getFavoriteSocialDeals().collect { localList ->
                val deals = localList.map { it.toSocialDeal() }
                emit(CustomResult.Success(deals))
            }
        } catch (e: Exception) {
            emit(CustomResult.Failure(e.localizedMessage ?: "Something went wrong"))
        }
    }
}