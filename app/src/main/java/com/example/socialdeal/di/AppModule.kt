package com.example.socialdeal.di

import com.example.core.data.remote.ApiService
import com.example.core.data.repositories.SocialDealListRepositoryImpl
import com.example.core.domain.repositories.SocialDealListRepository
import com.example.socialdeal.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    // Repositories ->
    @Provides
    fun provideSocialDealListRepository(
        apiService: ApiService
    ): SocialDealListRepository = SocialDealListRepositoryImpl(apiService = apiService)
}