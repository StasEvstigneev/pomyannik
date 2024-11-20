package com.example.prayforthem.listingdisplay.domain.models

import com.example.prayforthem.listings.domain.models.PersonDignityName

sealed class ListingDisplayScreenState {
    data object Loading: ListingDisplayScreenState()
    data class Content(
        val listingTitle: String,
        val list: List<PersonDignityName>)
        : ListingDisplayScreenState()
}