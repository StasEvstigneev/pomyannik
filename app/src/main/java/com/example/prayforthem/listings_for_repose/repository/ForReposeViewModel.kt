package com.example.prayforthem.listings_for_repose.repository

import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.PersonInteractor
import com.example.prayforthem.listings_for_health.presentation.ForHealthViewModel

class ForReposeViewModel(
    listingInteractor: ListingInteractor,
    personInteractor: PersonInteractor,
    isForHealth: Boolean
) : ForHealthViewModel(listingInteractor, personInteractor, isForHealth) {


}