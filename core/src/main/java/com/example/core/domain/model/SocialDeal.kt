package com.example.core.domain.model

data class SocialDeal(
    val city: String,
    val company: String,
    val image: String,
    val oldPrice: String,
    val newPrice: String,
    val sold: String,
    val title: String,
    val currencySign: String,
    val id: String,
    val description: String,
    val isFavorite: Boolean
)
