package com.example.prayforthem.di

import androidx.room.Room
import com.example.prayforthem.db.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<AppDatabase> {
        Room
            .databaseBuilder(androidContext(), AppDatabase::class.java, DB_NAME)
            .createFromAsset(DB_ASSETS_PATH)
            .fallbackToDestructiveMigration()
            .build()
    }

}

private const val DB_NAME = "database.db"
private const val DB_ASSETS_PATH = "database/praydb.db"