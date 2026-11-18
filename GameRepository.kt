package com.finalnight.game.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.gameDataStore by preferencesDataStore(name = "final_night_settings")

class GameRepository(private val context: Context) {
    private val bestTimeKey = intPreferencesKey("best_survival_time")

    val bestTime: Flow<Int> = context.gameDataStore.data.map { preferences ->
        preferences[bestTimeKey] ?: 0
    }

    suspend fun saveBestTime(seconds: Int) {
        context.gameDataStore.edit { preferences ->
            val previous = preferences[bestTimeKey] ?: 0
            if (seconds > previous) {
                preferences[bestTimeKey] = seconds
            }
        }
    }
}
