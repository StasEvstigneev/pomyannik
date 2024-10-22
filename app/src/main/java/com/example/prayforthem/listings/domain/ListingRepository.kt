package com.example.prayforthem.listings.domain

import com.example.prayforthem.listings.domain.models.Listing
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import kotlinx.coroutines.flow.Flow

interface ListingRepository {

    suspend fun saveListing(listing: Listing): Long

    suspend fun getListingById(id: Int): ListingWithPerson

    fun getListings(isForHealth: Boolean): Flow<List<ListingWithPerson>>

    suspend fun deleteListing(listing: ListingWithPerson)

}