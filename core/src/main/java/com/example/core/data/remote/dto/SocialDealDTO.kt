package com.example.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class SocialDealDTO(
    @SerializedName("city") val city: String?,
    @SerializedName("company") val company: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("prices") val prices: PricesDTO?,
    @SerializedName("sold_label") val sold: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("unique") val id: String
)