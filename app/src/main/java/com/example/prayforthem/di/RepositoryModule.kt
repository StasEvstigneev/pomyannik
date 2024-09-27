package com.example.prayforthem.di

import com.example.prayforthem.names.data.DignityRepositoryImpl
import com.example.prayforthem.names.data.NamesRepositoryImpl
import com.example.prayforthem.names.domain.DignityRepository
import com.example.prayforthem.names.domain.NamesRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<NamesRepository> {
        NamesRepositoryImpl(get(), get())
    }

    single<DignityRepository> {
        DignityRepositoryImpl(get(), get())
    }

}