package com.example.prayforthem.settings.domain.impl

import com.example.prayforthem.settings.domain.SettingsInteractor
import com.example.prayforthem.settings.domain.SettingsRepository
import com.example.prayforthem.settings.domain.models.ThemeSettings

class SettingsInteractorImpl(private val repository: SettingsRepository) : SettingsInteractor {
    override fun getThemeSettings(): ThemeSettings {
        return repository.getThemeSettings()
    }

    override fun saveThemeSettings(settings: ThemeSettings) {
        repository.saveThemeSettings(settings)
    }
}