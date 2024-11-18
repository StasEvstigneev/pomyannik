package com.example.prayforthem.settings.data

import com.example.prayforthem.settings.domain.SettingsRepository
import com.example.prayforthem.settings.domain.models.ThemeSettings
import com.example.prayforthem.storage.domain.LocalStorage

class SettingsRepositoryImpl(private val localStorage: LocalStorage) : SettingsRepository {
    override fun getThemeSettings(): ThemeSettings {
        val themeCode = localStorage.getThemeSettings(key = THEME_KEY)
        return when (themeCode) {
            1 -> ThemeSettings.LIGHT
            2 -> ThemeSettings.DARK
            else -> ThemeSettings.SYSTEM
        }
    }

    override fun saveThemeSettings(settings: ThemeSettings) {
        localStorage.saveThemeSettings(THEME_KEY, settings.themeCode)
    }

    companion object {
        private const val THEME_KEY = "theme"
    }
}