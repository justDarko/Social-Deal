package com.example.core.data

import com.example.core.data.local.entity.SocialDealEntity
import com.example.core.data.remote.dto.SocialDealDTO
import com.example.core.domain.model.SocialDeal

fun SocialDealDTO.toSocialDealEntity() = SocialDealEntity(
    id = id,
    title = title ?: "N/A",
    oldPrice = prices?.fromPrice?.amount.toString(),
    newPrice = prices?.price?.amount.toString(),
    city = city ?: "N/A",
    company = company ?: "N/A",
    sold = sold ?: "N/A",
    currencySign = prices?.price?.currency?.symbol ?: "N/A",
    image = image ?: "N/A",
    isFavorite = false
)

fun SocialDealDTO.toSocialDeal() = SocialDeal(
    id = id,
    title = title ?: "N/A",
    oldPrice = prices?.fromPrice?.amount.toString(),
    newPrice = prices?.price?.amount.toString(),
    city = city ?: "N/A",
    company = company ?: "N/A",
    sold = sold ?: "N/A",
    currencySign = prices?.price?.currency?.symbol ?: "N/A",
    image = image ?: "N/A",
    isFavorite = false,
    description = description ?: ""
)

fun SocialDeal.toSocialDealEntity() = SocialDealEntity(
    id = id,
    title = title,
    oldPrice = oldPrice,
    newPrice = newPrice,
    city = city,
    company = company,
    sold = sold,
    currencySign = currencySign,
    image = image,
    isFavorite = isFavorite
)

fun SocialDealEntity.toSocialDeal() = SocialDeal(
    id = id,
    title = title,
    oldPrice = oldPrice,
    newPrice = newPrice,
    city = city,
    company = company,
    sold = sold,
    currencySign = currencySign,
    image = image,
    isFavorite = isFavorite,
    description = ""
)