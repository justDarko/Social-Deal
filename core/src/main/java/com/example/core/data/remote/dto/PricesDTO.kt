package com.example.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PricesDTO(
    @SerializedName("discount_label") val discount: String?,
    @SerializedName("from_price") val fromPrice: PriceDTO?,
    @SerializedName("price") val price: PriceDTO?
)