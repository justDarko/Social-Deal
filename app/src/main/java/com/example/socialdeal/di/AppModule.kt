package com.example.socialdeal.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.local.db.SocialDealDatabase
import com.example.core.data.local.db.SocialDealsDao
import com.example.core.data.remote.ApiService
import com.example.core.data.repositories.FavoriteSocialDealListRepositoryImpl
import com.example.core.data.repositories.SocialDealDetailsRepositoryImpl
import com.example.core.data.repositories.SocialDealListRepositoryImpl
import com.example.core.domain.repositories.FavoriteSocialDealListRepository
import com.example.core.domain.repositories.SocialDealDetailsRepository
import com.example.core.domain.repositories.SocialDealListRepository
import com.example.socialdeal.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    // Repositories ->
    @Provides
    fun provideSocialDealListRepository(
        apiService: ApiService, socialDealsDao: SocialDealsDao
    ): SocialDealListRepository = SocialDealListRepositoryImpl(
        apiService = apiService, socialDealsDao = socialDealsDao
    )

    @Provides
    fun provideFavoriteSocialDealListRepository(
        socialDealsDao: SocialDealsDao
    ): FavoriteSocialDealListRepository = FavoriteSocialDealListRepositoryImpl(
        socialDealsDao = socialDealsDao
    )

    @Provides
    fun provideSocialDealDetailsRepository(
        apiService: ApiService,
        socialDealsDao: SocialDealsDao
    ): SocialDealDetailsRepository = SocialDealDetailsRepositoryImpl(
        apiService = apiService,
        socialDealsDao = socialDealsDao
    )

    // Room ->
    @Singleton
    @Provides
    fun provideGoalsDatabase(@ApplicationContext context: Context): SocialDealDatabase =
        Room.databaseBuilder(
            context = context, klass = SocialDealDatabase::class.java, name = "social_deal_db"
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideGoalsDao(socialDealDatabase: SocialDealDatabase): SocialDealsDao =
        socialDealDatabase.socialDealsDao()
}