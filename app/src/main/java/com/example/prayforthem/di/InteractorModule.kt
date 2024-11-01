package com.example.prayforthem.di

import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.PersonInteractor
import com.example.prayforthem.listings.domain.impl.ListingInteractorImpl
import com.example.prayforthem.listings.domain.impl.PersonInteractorImpl
import com.example.prayforthem.names.domain.DignityInteractor
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.names.domain.impl.DignityInteractorImpl
import com.example.prayforthem.names.domain.impl.NamesInteractorImpl
import com.example.prayforthem.prayerdisplay.domain.PrayerContentInteractor
import com.example.prayforthem.prayerdisplay.impl.PrayerContentInteractorImpl
import com.example.prayforthem.prayers.domain.PrayersInteractor
import com.example.prayforthem.prayers.domain.impl.PrayersInteractorImpl
import com.example.prayforthem.prayerscategories.domain.PrayersCategoriesInteractor
import com.example.prayforthem.prayerscategories.domain.impl.PrayersCategoriesInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    factory<NamesInteractor> {
        NamesInteractorImpl(get())
    }

    factory<DignityInteractor> {
        DignityInteractorImpl(get())
    }

    factory<PersonInteractor> {
        PersonInteractorImpl(get())
    }

    factory<ListingInteractor> {
        ListingInteractorImpl(get())
    }

    factory<PrayersCategoriesInteractor> {
        PrayersCategoriesInteractorImpl(get())
    }

    factory<PrayersInteractor> {
        PrayersInteractorImpl(get())
    }

    factory<PrayerContentInteractor> {
        PrayerContentInteractorImpl(get())
    }

}