package com.example.prayforthem.listings.domain.models


data class Person(
    val id: Int? = null,
    val idDignity: Int? = null,
    val idName: Int,
    val parentListingId: Int
)