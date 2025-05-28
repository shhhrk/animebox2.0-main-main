package com.example.animebox.data.datasource

import android.content.Context
import android.content.SharedPreferences

class PreferenceDataSource(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getCrystals(): Int {
        return prefs.getInt(CRYSTALS_KEY, 0)
    }

    fun saveCrystals(crystals: Int) {
        prefs.edit().putInt(CRYSTALS_KEY, crystals).apply()
    }

    // Add methods for saving/loading obtained characters here if needed in the future
    // Example:
    /*
    fun getObtainedCharactersJson(): String? {
        return prefs.getString(OBTAINED_CHARACTERS_KEY, null)
    }

    fun saveObtainedCharactersJson(json: String) {
        prefs.edit().putString(OBTAINED_CHARACTERS_KEY, json).apply()
    }
    */

    companion object {
        private const val PREFS_NAME = "GamePrefs"
        private const val CRYSTALS_KEY = "CRYSTALS"
        // private const val OBTAINED_CHARACTERS_KEY = "OBTAINED_CHARACTERS"
    }
} 