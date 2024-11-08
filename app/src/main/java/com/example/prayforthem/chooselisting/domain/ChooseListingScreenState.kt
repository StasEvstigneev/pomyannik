package com.example.prayforthem.chooselisting.domain

import com.example.prayforthem.listings.domain.models.ListingWithPerson

sealed class ChooseListingScreenState {
    data object Loading : ChooseListingScreenState()
    data class Content(val list: List<ListingWithPerson>) : ChooseListingScreenState()
    data object Placeholder : ChooseListingScreenState()
}