package com.example.prayforthem.di

import com.example.prayforthem.articledisplay.domain.ArticleContentInteractor
import com.example.prayforthem.articledisplay.domain.impl.ArticleContentInteractorImpl
import com.example.prayforthem.listingdisplay.domain.SharingInteractor
import com.example.prayforthem.listingdisplay.domain.impl.SharingInteractorImpl
import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.PersonInteractor
import com.example.prayforthem.listings.domain.impl.ListingInteractorImpl
import com.example.prayforthem.listings.domain.impl.PersonInteractorImpl
import com.example.prayforthem.names.domain.DignityInteractor
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.names.domain.impl.DignityInteractorImpl
import com.example.prayforthem.names.domain.impl.NamesInteractorImpl
import com.example.prayforthem.prayeraddnames.domain.TempPersonInteractor
import com.example.prayforthem.prayeraddnames.domain.impl.TempPersonInteractorImpl
import com.example.prayforthem.prayerdisplay.domain.PrayerContentInteractor
import com.example.prayforthem.prayerdisplay.impl.PrayerContentInteractorImpl
import com.example.prayforthem.prayers.domain.PrayersInteractor
import com.example.prayforthem.prayers.domain.impl.PrayersInteractorImpl
import com.example.prayforthem.prayerscategories.domain.PrayersCategoriesInteractor
import com.example.prayforthem.prayerscategories.domain.impl.PrayersCategoriesInteractorImpl
import com.example.prayforthem.settings.domain.SettingsInteractor
import com.example.prayforthem.settings.domain.impl.SettingsInteractorImpl
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

    factory<TempPersonInteractor> {
        TempPersonInteractorImpl(get())
    }

    factory<ArticleContentInteractor> {
        ArticleContentInteractorImpl(get())
    }

    factory<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    factory<SharingInteractor> {
        SharingInteractorImpl(get())
    }

}