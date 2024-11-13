package com.example.prayforthem.pomyannikusopsrod.presentation

import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.names.domain.DignityInteractor
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.prayeraddnames.domain.TempPersonInteractor
import com.example.prayforthem.prayeraddnames.presentation.PrayerAddNamesViewModel

class PomyannikUsopSrodViewModel(
    private val listingInteractor: ListingInteractor,
    private val tempPersonInteractor: TempPersonInteractor,
    private val namesInteractor: NamesInteractor,
    private val dignityInteractor: DignityInteractor
) : PrayerAddNamesViewModel(
    true,
    listingInteractor,
    tempPersonInteractor,
    namesInteractor,
    dignityInteractor
) {
    override fun getListingId(): Int {
        return USOP_SROD_LIST
    }

    companion object {
        private const val USOP_SROD_LIST = 2
    }
}