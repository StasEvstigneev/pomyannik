package com.example.prayforthem.listings.domain.models

data class ListingWithPerson(
    val listing: Listing,
    val personListing: List<PersonDignityName>
)
