package com.example.prayforthem.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prayforthem.settings.domain.SettingsInteractor
import com.example.prayforthem.settings.domain.models.SettingsScreenState
import com.example.prayforthem.settings.domain.models.ThemeSettings

class SettingsViewModel(
    private val settingsInteractor: SettingsInteractor
) : ViewModel() {

    private val screenState = MutableLiveData<SettingsScreenState>(SettingsScreenState.Loading)
    fun getScreenState(): LiveData<SettingsScreenState> = screenState

    init {
        getTheme()
    }

    private fun getTheme() {
        val theme = settingsInteractor.getThemeSettings()
        screenState.postValue(SettingsScreenState.Content(theme))
    }

    fun changeTheme(settings: ThemeSettings) {
        screenState.postValue(SettingsScreenState.Loading)
        settingsInteractor.saveThemeSettings(settings)
        screenState.postValue(SettingsScreenState.Content(settings))
    }


}