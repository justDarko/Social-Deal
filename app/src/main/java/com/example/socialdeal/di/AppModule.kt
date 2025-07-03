package com.example.socialdeal.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.core.data.SOCIAL_DEAL_DB
import com.example.core.data.USER_PREFERENCES
import com.example.core.data.local.dataStore.DataStoreManager
import com.example.core.data.local.db.SocialDealDatabase
import com.example.core.data.local.db.SocialDealsDao
import com.example.core.data.remote.ApiService
import com.example.core.data.repositories.FavoriteSocialDealListRepositoryImpl
import com.example.core.data.repositories.SettingsRepositoryImpl
import com.example.core.data.repositories.SocialDealDetailsRepositoryImpl
import com.example.core.data.repositories.SocialDealListRepositoryImpl
import com.example.core.domain.repositories.FavoriteSocialDealListRepository
import com.example.core.domain.repositories.SettingsRepository
import com.example.core.domain.repositories.SocialDealDetailsRepository
import com.example.core.domain.repositories.SocialDealListRepository
import com.example.socialdeal.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
        apiService: ApiService,
        socialDealsDao: SocialDealsDao,
        dataStoreManager: DataStoreManager
    ): SocialDealListRepository = SocialDealListRepositoryImpl(
        apiService = apiService,
        socialDealsDao = socialDealsDao,
        dataStoreManager = dataStoreManager
    )

    @Provides
    fun provideFavoriteSocialDealListRepository(
        socialDealsDao: SocialDealsDao,
        dataStoreManager: DataStoreManager
    ): FavoriteSocialDealListRepository = FavoriteSocialDealListRepositoryImpl(
        socialDealsDao = socialDealsDao,
        dataStoreManager = dataStoreManager
    )

    @Provides
    fun provideSocialDealDetailsRepository(
        apiService: ApiService,
        socialDealsDao: SocialDealsDao,
        dataStoreManager: DataStoreManager
    ): SocialDealDetailsRepository = SocialDealDetailsRepositoryImpl(
        apiService = apiService,
        socialDealsDao = socialDealsDao,
        dataStoreManager = dataStoreManager
    )

    @Provides
    fun provideSettingsRepository(
        dataStoreManager: DataStoreManager
    ): SettingsRepository = SettingsRepositoryImpl(
        dataStoreManager = dataStoreManager
    )

    // Room ->
    @Singleton
    @Provides
    fun provideGoalsDatabase(@ApplicationContext context: Context): SocialDealDatabase =
        Room.databaseBuilder(
            context = context, klass = SocialDealDatabase::class.java, name = SOCIAL_DEAL_DB
        ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideGoalsDao(socialDealDatabase: SocialDealDatabase): SocialDealsDao =
        socialDealDatabase.socialDealsDao()

    // Data Store ->
    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            // SupervisorJob allows for child coroutine to continue running in case
            // other child coroutine failed. So if one read/write operation fails,
            // the rest of the work continues
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }
}