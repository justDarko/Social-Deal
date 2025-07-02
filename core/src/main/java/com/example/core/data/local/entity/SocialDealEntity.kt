package com.example.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "social_deal_tbl")
data class SocialDealEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val city: String,
    val company: String,
    val image: String,
    val oldPrice: String,
    val newPrice: String,
    val sold: String,
    val title: String,
    val currencySign: String,
    val isFavorite: Boolean = false
)
