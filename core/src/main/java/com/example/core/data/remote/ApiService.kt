package com.example.core.data.remote

import com.example.core.data.remote.responseModels.SocialDealListResponse
import retrofit2.http.GET

interface ApiService {

    @GET("deals.json")
    suspend fun getSocialDealsList(): SocialDealListResponse

}