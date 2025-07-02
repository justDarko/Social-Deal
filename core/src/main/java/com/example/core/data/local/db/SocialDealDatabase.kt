package com.example.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core.data.local.entity.SocialDealEntity

@Database(entities = [SocialDealEntity::class], version = 1)
abstract class SocialDealDatabase : RoomDatabase() {
    abstract fun socialDealsDao(): SocialDealsDao

}