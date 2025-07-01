package com.example.core.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrencyDTO(
    @SerializedName("code")
    val code: String?,
    @SerializedName("symbol")
    val symbol: String?
)