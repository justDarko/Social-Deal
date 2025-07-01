package com.example.core.data

import com.example.core.data.remote.dto.SocialDealDTO
import com.example.core.domain.model.SocialDeal

fun SocialDealDTO.toSocialDeal() = SocialDeal(
    id = id,
    title = title ?: "N/A",
    fromPrice = prices?.fromPrice?.amount.toString(),
    currentPrice = prices?.price?.amount.toString(),
    city = city ?: "N/A",
    company = company ?: "N/A",
    sold = sold ?: "N/A",
    currencySign = prices?.price?.currency?.symbol ?: "N/A",
    image = image ?: "N/A"
)