package com.example.prayforthem.listings.domain.models


data class Person(
    val id: Int?,
    val idDignity: Int?,
    val idName: Int,
    val parentListingId: Int
)