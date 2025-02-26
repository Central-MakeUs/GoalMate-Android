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

class UserInfoDataStore
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_INFO_DATASTORE_NAME)

        val userNickName: Flow<String> =
            context.dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }.map { preferences ->
                    preferences[keyNickName] ?: ""
                }

        suspend fun saveNickName(value: String) {
            context.dataStore.edit { preferences ->
                preferences[keyNickName] = value
            }
        }

        suspend fun deleteNickName() {
            context.dataStore.edit { preferences ->
                preferences.remove(keyNickName)
            }
        }

        private companion object {
            const val USER_INFO_DATASTORE_NAME = "userInfoDataStore"
            val keyNickName = stringPreferencesKey("KEY_NICK_NAME")
        }
    }
