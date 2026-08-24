package com.dev.caiovinicius.rocketia.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_SETTINGS_DATASTORE_NAME = "user_settings"
private const val SELECTED_STACK_KEY = "selected_stack"
private val SELECTED_STACK_PREFERENCES_KEY = stringPreferencesKey(SELECTED_STACK_KEY)
private const val FIRST_LAUNCH_KEY = "FIRST_LAUNCH"
private val FIRST_LAUNCH_PREFERENCES_KEY = booleanPreferencesKey(FIRST_LAUNCH_KEY)

class UserSettingsDataStorePreferencesImpl(
    private val context: Context,
) : UserSettingsPreferences {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        USER_SETTINGS_DATASTORE_NAME
    )

    override val selectedStack: Flow<String>
        get() = context.dataStore.data.map { preferences ->
            preferences[SELECTED_STACK_PREFERENCES_KEY].orEmpty()
        }

    override suspend fun changeSelectedStack(stack: String) {
        context.dataStore.edit { settings ->
            settings[SELECTED_STACK_PREFERENCES_KEY] = stack
        }
    }

    override val firstLaunch: Flow<Boolean>
        get() = context.dataStore.data.map { preferences ->
            preferences[FIRST_LAUNCH_PREFERENCES_KEY] ?: true
        }

    override suspend fun changeFirstLaunch() {
        context.dataStore.edit { settings ->
            val currentFirstLaunch = settings[FIRST_LAUNCH_PREFERENCES_KEY] ?: true
            settings[FIRST_LAUNCH_PREFERENCES_KEY] = !currentFirstLaunch
        }
    }
}