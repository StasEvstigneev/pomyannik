package com.example.prayforthem.settings.domain

import com.example.prayforthem.settings.domain.models.ThemeSettings

interface SettingsInteractor {

    fun getThemeSettings(): ThemeSettings

    fun saveThemeSettings(settings: ThemeSettings)

}