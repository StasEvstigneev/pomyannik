package com.example.prayforthem.di

import com.example.prayforthem.addname.presentation.AddNameViewModel
import com.example.prayforthem.createlisting.presentation.CreateListingViewModel
import com.example.prayforthem.editlisting.presentation.EditListingViewModel
import com.example.prayforthem.information.presentation.InfoViewModel
import com.example.prayforthem.listingdisplay.presentation.ListingDisplayViewModel
import com.example.prayforthem.listings.presentation.ListingsViewModel
import com.example.prayforthem.listings_for_health.presentation.ForHealthViewModel
import com.example.prayforthem.listings_for_repose.presentation.ForReposeViewModel
import com.example.prayforthem.names.presentation.NamesViewModel
import com.example.prayforthem.prayeraddnames.presentation.PrayerAddNamesViewModel
import com.example.prayforthem.prayerdisplay.presentation.PrayerDisplayViewModel
import com.example.prayforthem.prayers.presentation.PrayersViewModel
import com.example.prayforthem.prayerscategories.presentation.PrayersCategoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<ListingsViewModel> {
        ListingsViewModel()
    }

    viewModel<ForHealthViewModel> {
        ForHealthViewModel(get(), get(), FOR_HEALTH)
    }

    viewModel<ForReposeViewModel> {
        ForReposeViewModel(get(), get(), FOR_REPOSE)
    }

    viewModel<PrayersCategoriesViewModel> {
        PrayersCategoriesViewModel(get())
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

    viewModel<ListingDisplayViewModel> { (isForHealth: Boolean, listingId: Int) ->
        ListingDisplayViewModel(isForHealth, listingId, get())
    }

    viewModel<EditListingViewModel> { (listingId: Int) ->
        EditListingViewModel(listingId, get(), get(), get(), get())
    }

    viewModel<PrayersViewModel> { (categoryId: Int) ->
        PrayersViewModel(categoryId, get())
    }

    viewModel<PrayerDisplayViewModel> { (prayerFileName: String) ->
        PrayerDisplayViewModel(prayerFileName, get(), get())
    }

    viewModel<PrayerAddNamesViewModel> { (forHealth: Boolean) ->
        PrayerAddNamesViewModel(forHealth, get(), get(), get(), get())
    }

}

private const val FOR_HEALTH = true
private const val FOR_REPOSE = false