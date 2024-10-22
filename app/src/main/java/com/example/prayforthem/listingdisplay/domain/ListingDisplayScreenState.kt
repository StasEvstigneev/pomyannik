package com.example.prayforthem.listingdisplay.domain

import com.example.prayforthem.listings.domain.models.PersonDignityName

sealed class ListingDisplayScreenState {
    data object Loading: ListingDisplayScreenState()
    data class Content(val list: List<PersonDignityName>): ListingDisplayScreenState()
}