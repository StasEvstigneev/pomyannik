package com.example.prayforthem.settings.domain.models

sealed class SettingsScreenState {
    data object Loading : SettingsScreenState()
    data class Content(val themeSettings: ThemeSettings) : SettingsScreenState()
}