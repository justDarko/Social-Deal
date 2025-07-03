package com.example.core.data.local.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.core.data.CustomResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val USER_CURRENCY = stringPreferencesKey("USER_CURRENCY")
    }

    init {
        Timber.d("Hello form DataStoreManager")
    }

    suspend fun clearPreferences() {
        // In case we decide to have login feature later on
        dataStore.edit {
            it.clear()
        }
    }

    suspend fun setUserCurrency(userCurrency: String) {
        dataStore.edit { preferences ->
            preferences[USER_CURRENCY] = userCurrency
        }
    }

    fun getUserCurrency(): Flow<CustomResult<String>> = flow {
        try {
            dataStore.data.collect { preferences ->
                emit(CustomResult.Success(preferences[USER_CURRENCY] ?: "EUR"))
            }
        } catch (e: Exception) {
            emit(CustomResult.Failure(e.localizedMessage ?: "Something went wring"))
        }
    }.catch { e ->
        emit(CustomResult.Failure(e.localizedMessage ?: "Something went wrong"))
    }
}