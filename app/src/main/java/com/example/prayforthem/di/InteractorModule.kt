package com.example.prayforthem.di

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

}