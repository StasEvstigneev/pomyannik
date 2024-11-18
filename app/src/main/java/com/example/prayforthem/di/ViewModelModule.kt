package com.example.prayforthem.di

import com.example.prayforthem.addname.presentation.AddNameViewModel
import com.example.prayforthem.articledisplay.presentation.ArticleDisplayViewModel
import com.example.prayforthem.chooselisting.presentation.ChooseListingViewModel
import com.example.prayforthem.createlisting.presentation.CreateListingViewModel
import com.example.prayforthem.editlisting.presentation.EditListingViewModel
import com.example.prayforthem.information.presentation.InfoViewModel
import com.example.prayforthem.listingdisplay.presentation.ListingDisplayViewModel
import com.example.prayforthem.listings.presentation.ListingsViewModel
import com.example.prayforthem.listings_for_health.presentation.ForHealthViewModel
import com.example.prayforthem.listings_for_repose.presentation.ForReposeViewModel
import com.example.prayforthem.names.presentation.NamesViewModel
import com.example.prayforthem.pomyannikdisplay.presentation.PomyannikDisplayViewModel
import com.example.prayforthem.pomyannikdrugi.presentation.PomyannikDrugiViewModel
import com.example.prayforthem.pomyannikduhotets.presentation.PomyannikDuhOtetsViewModel
import com.example.prayforthem.pomyannikroditeli.presentation.PomyannikRoditeliViewModel
import com.example.prayforthem.pomyanniksrodniki.presentation.PomyannikSrodnikiViewModel
import com.example.prayforthem.pomyannikusopsrod.presentation.PomyannikUsopSrodViewModel
import com.example.prayforthem.prayeraddnames.presentation.PrayerAddNamesViewModel
import com.example.prayforthem.prayerdisplay.presentation.PrayerDisplayViewModel
import com.example.prayforthem.prayers.presentation.PrayersViewModel
import com.example.prayforthem.prayerscategories.presentation.PrayersCategoriesViewModel
import com.example.prayforthem.settings.presentation.SettingsViewModel
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

    viewModel<PrayerDisplayViewModel> { (isForHealth: Boolean, prayerFileName: String) ->
        PrayerDisplayViewModel(isForHealth, prayerFileName, get(), get(), get(), get())
    }

    viewModel<PrayerAddNamesViewModel> { (forHealth: Boolean) ->
        PrayerAddNamesViewModel(forHealth, get(), get(), get(), get())
    }

    viewModel<ChooseListingViewModel> { (forHealth: Boolean) ->
        ChooseListingViewModel(forHealth, get())
    }

    viewModel<PomyannikDuhOtetsViewModel> {
        PomyannikDuhOtetsViewModel(get(), get(), get(), get())
    }

    viewModel<PomyannikRoditeliViewModel> {
        PomyannikRoditeliViewModel(get(), get(), get(), get())
    }

    viewModel<PomyannikSrodnikiViewModel> {
        PomyannikSrodnikiViewModel(get(), get(), get(), get())
    }

    viewModel<PomyannikDrugiViewModel> {
        PomyannikDrugiViewModel(get(), get(), get(), get())
    }

    viewModel<PomyannikUsopSrodViewModel> {
        PomyannikUsopSrodViewModel(get(), get(), get(), get())
    }

    viewModel<PomyannikDisplayViewModel> { (prayerFileName: String) ->
        PomyannikDisplayViewModel(prayerFileName, get(), get(), get(), get())
    }

    viewModel<ArticleDisplayViewModel> { (prayerFileName: String) ->
        ArticleDisplayViewModel(prayerFileName, get(), get())
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(get())
    }

}

private const val FOR_HEALTH = true
private const val FOR_REPOSE = false