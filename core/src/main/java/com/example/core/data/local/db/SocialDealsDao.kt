package com.example.core.data.local.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.core.data.local.entity.SocialDealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SocialDealsDao {
    // Flows are used because our UI will update itself automatically
    // in case we like or dislike social deal
    @Query("SELECT * FROM social_deal_tbl")
    fun getSocialDealList(): Flow<List<SocialDealEntity>>

    @Query("SELECT * FROM social_deal_tbl WHERE isFavorite = 1")
    fun getFavoriteSocialDeals(): Flow<List<SocialDealEntity>>

    @Upsert
    suspend fun upsertSocialDeals(socialDeals: List<SocialDealEntity>)

    @Update
    suspend fun updateSocialDeal(socialDeal: SocialDealEntity)
}