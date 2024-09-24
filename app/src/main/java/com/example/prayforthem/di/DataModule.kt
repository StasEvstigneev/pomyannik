package com.example.prayforthem.di

import androidx.room.Room
import com.example.prayforthem.db.AppDatabase
import com.example.prayforthem.db.converters.BooleanIntDbConverter
import com.example.prayforthem.db.converters.DignityDbConverter
import com.example.prayforthem.db.converters.NameDbConverter
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

    factory<BooleanIntDbConverter> {
        BooleanIntDbConverter()
    }

    factory<NameDbConverter> {
        NameDbConverter(get())
    }

    factory<DignityDbConverter> {
        DignityDbConverter(get())
    }

}

private const val DB_NAME = "database.db"
private const val DB_ASSETS_PATH = "database/praydb.db"