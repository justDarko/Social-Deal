package com.example.core.data.remote

import com.example.core.data.remote.dto.SocialDealDTO
import com.example.core.data.remote.responseModels.SocialDealListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("deals.json")
    suspend fun getSocialDealsList(): SocialDealListResponse

    @GET("details.json")
    suspend fun getSocialDealDetails(
        @Query("id") id: String,
    ): SocialDealDTO

}