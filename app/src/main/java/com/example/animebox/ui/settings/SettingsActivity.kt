package com.example.animebox.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.animebox.R

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backBtn = findViewById<android.widget.ImageButton>(R.id.btnBack)
        backBtn.setOnClickListener { finish() }

        val themeSwitch = findViewById<Switch>(R.id.switchTheme)
        val themeText = findViewById<android.widget.TextView>(R.id.textViewTheme)
        val prefs = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val isDark = prefs.getBoolean("DARK_THEME", false)
        themeSwitch.isChecked = isDark
        themeText.text = if (isDark) "Тёмная тема" else "Светлая тема"

        themeSwitch.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themeText.text = "Тёмная тема"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                themeText.text = "Светлая тема"
            }
            prefs.edit().putBoolean("DARK_THEME", isChecked).apply()
        }
    }
} 