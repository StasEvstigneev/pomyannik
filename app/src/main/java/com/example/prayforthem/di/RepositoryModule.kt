package com.example.prayforthem.di

import com.example.prayforthem.articledisplay.data.ArticleContentRepositoryImpl
import com.example.prayforthem.articledisplay.domain.ArticleContentRepository
import com.example.prayforthem.listingdisplay.data.ExternalNavigatorImpl
import com.example.prayforthem.listingdisplay.domain.ExternalNavigator
import com.example.prayforthem.listings.data.ListingRepositoryImpl
import com.example.prayforthem.listings.data.PersonRepositoryImpl
import com.example.prayforthem.listings.domain.ListingRepository
import com.example.prayforthem.listings.domain.PersonRepository
import com.example.prayforthem.names.data.DignityRepositoryImpl
import com.example.prayforthem.names.data.NamesRepositoryImpl
import com.example.prayforthem.names.domain.DignityRepository
import com.example.prayforthem.names.domain.NamesRepository
import com.example.prayforthem.prayeraddnames.data.TempPersonRepositoryImpl
import com.example.prayforthem.prayeraddnames.domain.TempPersonRepository
import com.example.prayforthem.prayerdisplay.data.PrayerContentRepositoryImpl
import com.example.prayforthem.prayerdisplay.domain.PrayerContentRepository
import com.example.prayforthem.prayers.data.PrayersRepositoryImpl
import com.example.prayforthem.prayers.domain.PrayersRepository
import com.example.prayforthem.prayerscategories.data.PrayersCategoriesRepositoryImpl
import com.example.prayforthem.prayerscategories.domain.PrayersCategoriesRepository
import com.example.prayforthem.settings.data.SettingsRepositoryImpl
import com.example.prayforthem.settings.domain.SettingsRepository
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

    single<PrayersRepository> {
        PrayersRepositoryImpl(get(), get())
    }

    single<PrayerContentRepository> {
        PrayerContentRepositoryImpl(get())
    }

    single<TempPersonRepository> {
        TempPersonRepositoryImpl(get(), get())
    }

    single<ArticleContentRepository> {
        ArticleContentRepositoryImpl(get())
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get())
    }

    single<ExternalNavigator> {
        ExternalNavigatorImpl(get())
    }

}