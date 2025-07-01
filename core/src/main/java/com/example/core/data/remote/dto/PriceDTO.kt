package com.example.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PriceDTO(
    @SerializedName("amount") val amount: Double?,
    @SerializedName("currency") val currency: CurrencyDTO?
)