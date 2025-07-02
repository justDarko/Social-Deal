package com.example.core.data.remote.responseModels

import com.example.core.data.remote.dto.SocialDealDTO
import com.google.gson.annotations.SerializedName

data class SocialDealListResponse(
    @SerializedName("num_deals") val numberOfDeals: Int,
    @SerializedName("deals") val deals: List<SocialDealDTO>
)
