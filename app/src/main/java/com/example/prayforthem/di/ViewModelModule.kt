package com.example.prayforthem.di

import com.example.prayforthem.createlist.presentation.CreateListViewModel
import com.example.prayforthem.information.presentation.InfoViewModel
import com.example.prayforthem.lists.presentation.ListsViewModel
import com.example.prayforthem.lists_for_health.presentation.ForHealthViewModel
import com.example.prayforthem.lists_for_repose.repository.ForReposeViewModel
import com.example.prayforthem.names.presentation.NamesViewModel
import com.example.prayforthem.prayers.presentation.PrayersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<ListsViewModel> {
        ListsViewModel()
    }

    viewModel<ForHealthViewModel> {
        ForHealthViewModel()
    }

    viewModel<ForReposeViewModel> {
        ForReposeViewModel()
    }

    viewModel<PrayersViewModel> {
        PrayersViewModel()
    }

    viewModel<InfoViewModel> {
        InfoViewModel()
    }

    viewModel<CreateListViewModel> {
        CreateListViewModel(get(), get())
    }

    viewModel<NamesViewModel> {
        NamesViewModel(get(), get())
    }
}