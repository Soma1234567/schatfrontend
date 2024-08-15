package com.soma.mychatapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.soma.mychatapp.domain.repository.DataStoreOperations
import com.soma.mychatapp.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.datastore: DataStore<Preferences> by preferencesDataStore(Constants.PREFERNCE_NAME)


class DataStoreOperationsImpl(
    private val context: Context
):DataStoreOperations {
    private object PreferencesKey {
        val usernameKey = stringPreferencesKey(Constants.PREFERENCE_KEY)
        val avatarKey = intPreferencesKey("avatar_key")

    }

    private val dataStore = context.datastore
    override suspend fun saveUsername(username: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.usernameKey] = username
        }
    }

    override fun readUsername(): Flow<String> {
        return dataStore.data.map {
            val username = it[PreferencesKey.usernameKey] ?: ""
            username
        }
    }

    override suspend fun saveAvatar(avatar: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.avatarKey] = avatar
        }
    }

    override fun readAvatar(): Flow<Int> {
        return dataStore.data.map {
            val username = it[PreferencesKey.avatarKey] ?: 0
            username
        }
    }

}