package com.example.prayforthem.listings.domain.models

sealed class ListingScreenState {
    data object Loading : ListingScreenState()
    data object Placeholder : ListingScreenState()
    data class Content(val listing: List<ListingWithPerson>) : ListingScreenState()
}