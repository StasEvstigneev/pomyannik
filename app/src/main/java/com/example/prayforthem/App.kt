package com.example.prayforthem

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.prayforthem.di.dataModule
import com.example.prayforthem.di.interactorModule
import com.example.prayforthem.di.repositoryModule
import com.example.prayforthem.di.viewModelModule
import com.example.prayforthem.settings.domain.SettingsRepository
import com.markodevcic.peko.PermissionRequester
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(interactorModule, dataModule, repositoryModule, viewModelModule)
        }
        val settingsRepository by inject<SettingsRepository>()
        switchTheme(settingsRepository.getThemeSettings().themeCode)
        PermissionRequester.initialize(applicationContext)
    }

    fun switchTheme(themeCode: Int) {
        when (themeCode) {
            LIGHT_THEME_CODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            DARK_THEME_CODE -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    companion object {
        private const val LIGHT_THEME_CODE = 1
        private const val DARK_THEME_CODE = 2
    }
}