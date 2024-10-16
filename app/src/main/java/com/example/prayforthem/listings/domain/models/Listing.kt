package com.example.prayforthem.listings.domain.models

data class Listing(
    val listingId: Int? = null,
    var title: String,
    val forHealth: Boolean
)