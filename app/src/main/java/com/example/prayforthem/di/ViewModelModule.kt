package com.example.prayforthem.di

import com.example.prayforthem.addname.presentation.AddNameViewModel
import com.example.prayforthem.createlisting.presentation.CreateListingViewModel
import com.example.prayforthem.information.presentation.InfoViewModel
import com.example.prayforthem.listings.presentation.ListingsViewModel
import com.example.prayforthem.listings_for_health.presentation.ForHealthViewModel
import com.example.prayforthem.listings_for_repose.repository.ForReposeViewModel
import com.example.prayforthem.names.presentation.NamesViewModel
import com.example.prayforthem.prayers.presentation.PrayersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<ListingsViewModel> {
        ListingsViewModel()
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

    viewModel<CreateListingViewModel> {
        CreateListingViewModel(get(), get(), get(), get())
    }

    viewModel<NamesViewModel> {
        NamesViewModel(get(), get())
    }

    viewModel<AddNameViewModel> {
        AddNameViewModel(get())
    }
}