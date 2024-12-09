package com.example.prayforthem.listings_for_repose.presentation

import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings_for_health.presentation.ForHealthViewModel

class ForReposeViewModel(
    listingInteractor: ListingInteractor,
    isForHealth: Boolean
) : ForHealthViewModel(listingInteractor, isForHealth) {


}