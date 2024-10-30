package com.example.prayforthem.di

import com.example.prayforthem.listings.data.ListingRepositoryImpl
import com.example.prayforthem.listings.data.PersonRepositoryImpl
import com.example.prayforthem.listings.domain.ListingRepository
import com.example.prayforthem.listings.domain.PersonRepository
import com.example.prayforthem.names.data.DignityRepositoryImpl
import com.example.prayforthem.names.data.NamesRepositoryImpl
import com.example.prayforthem.names.domain.DignityRepository
import com.example.prayforthem.names.domain.NamesRepository
import com.example.prayforthem.prayerscategories.data.PrayersCategoriesRepositoryImpl
import com.example.prayforthem.prayerscategories.domain.PrayersCategoriesRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<NamesRepository> {
        NamesRepositoryImpl(get(), get())
    }

    single<DignityRepository> {
        DignityRepositoryImpl(get(), get())
    }

    single<PersonRepository> {
        PersonRepositoryImpl(get(), get())
    }

    single<ListingRepository> {
        ListingRepositoryImpl(get(), get(), get())
    }

    single<PrayersCategoriesRepository> {
        PrayersCategoriesRepositoryImpl(get(), get())
    }

}