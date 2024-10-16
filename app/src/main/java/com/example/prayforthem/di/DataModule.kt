package com.example.prayforthem.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.prayforthem.db.AppDatabase
import com.example.prayforthem.db.converters.BooleanIntDbConverter
import com.example.prayforthem.db.converters.DignityDbConverter
import com.example.prayforthem.db.converters.ListingDbConverter
import com.example.prayforthem.db.converters.NameDbConverter
import com.example.prayforthem.db.converters.PersonDbConverter
import com.example.prayforthem.storage.data.LocalStorageImpl
import com.example.prayforthem.storage.domain.LocalStorage
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

    factory<PersonDbConverter> {
        PersonDbConverter(get(), get())
    }

    factory<ListingDbConverter> {
        ListingDbConverter(get(), get())
    }

    single<SharedPreferences> {
        androidContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }

    single<LocalStorage> {
        LocalStorageImpl(get())
    }

}

private const val DB_NAME = "database.db"
private const val DB_ASSETS_PATH = "database/praydb.db"
private const val SHARED_PREFS = "shared_preferences"