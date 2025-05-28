package com.example.animebox.data.repository

import android.content.Context
import android.content.SharedPreferences

class CrystalsRepository(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("GamePrefs", Context.MODE_PRIVATE)

    fun getCrystals(): Int = prefs.getInt("CRYSTALS", 0)

    fun saveCrystals(amount: Int) {
        prefs.edit().putInt("CRYSTALS", amount).apply()
    }

    fun addCrystals(amount: Int) {
        val current = getCrystals()
        saveCrystals(current + amount)
    }

    fun removeCrystals(amount: Int): Boolean {
        val current = getCrystals()
        if (current >= amount) {
            saveCrystals(current - amount)
            return true
        }
        return false
    }
} 