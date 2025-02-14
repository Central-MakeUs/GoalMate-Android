package cmc.goalmate.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class TokenDataStore
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = TOKEN_DATASTORE_NANE)

        val accessToken: Flow<String> =
            context.dataStore.data.catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[keyAccessToken] ?: ""
            }

        val refreshToken: Flow<String> =
            context.dataStore.data.catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }.map { preferences ->
                preferences[keyRefreshToken] ?: ""
            }

        suspend fun saveAccessToken(value: String) {
            context.dataStore.edit { preferences ->
                preferences[keyAccessToken] = value
            }
        }

        suspend fun saveRefreshToken(value: String) {
            context.dataStore.edit { preferences ->
                preferences[keyRefreshToken] = value
            }
        }

        suspend fun deleteAccessToken() {
            context.dataStore.edit { prefs ->
                prefs.remove(keyAccessToken)
            }
        }

        suspend fun deleteRefreshToken() {
            context.dataStore.edit { prefs ->
                prefs.remove(keyRefreshToken)
            }
        }

        private companion object {
            const val TOKEN_DATASTORE_NANE = "tokenDataStore"
            val keyAccessToken = stringPreferencesKey("KEY_ACCESS_TOKEN")
            val keyRefreshToken = stringPreferencesKey("KEY_REFRESH_TOKEN")
        }
    }
