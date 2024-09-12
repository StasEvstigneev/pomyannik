package com.example.prayforthem

import android.app.Application
import com.example.prayforthem.di.dataModule
import com.example.prayforthem.di.interactorModule
import com.example.prayforthem.di.repositoryModule
import com.example.prayforthem.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(interactorModule, dataModule, repositoryModule, viewModelModule)
        }
    }
}