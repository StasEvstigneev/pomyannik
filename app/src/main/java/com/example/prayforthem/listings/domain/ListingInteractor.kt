package com.example.prayforthem.listings.domain

import com.example.prayforthem.listings.domain.models.Listing
import com.example.prayforthem.listings.domain.models.ListingWithPerson

interface ListingInteractor {

    suspend fun saveListing(listing: Listing): Long

    suspend fun getListingById(id: Int): List<ListingWithPerson>
}