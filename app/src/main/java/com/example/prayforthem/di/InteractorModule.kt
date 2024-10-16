package com.example.prayforthem.di

import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.PersonInteractor
import com.example.prayforthem.listings.domain.impl.ListingInteractorImpl
import com.example.prayforthem.listings.domain.impl.PersonInteractorImpl
import com.example.prayforthem.names.domain.DignityInteractor
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.names.domain.impl.DignityInteractorImpl
import com.example.prayforthem.names.domain.impl.NamesInteractorImpl
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
}