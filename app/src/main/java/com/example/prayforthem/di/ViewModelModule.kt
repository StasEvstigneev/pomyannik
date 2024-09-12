package com.example.prayforthem.di

import com.example.prayforthem.lists.ListsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<ListsViewModel> {
        ListsViewModel()
    }

}