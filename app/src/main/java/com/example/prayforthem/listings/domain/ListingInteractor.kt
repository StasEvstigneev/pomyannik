package com.example.prayforthem.listings.domain

import com.example.prayforthem.listings.domain.models.Listing
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import kotlinx.coroutines.flow.Flow

interface ListingInteractor {

    suspend fun saveListing(listing: Listing): Long

    suspend fun getListingById(id: Int): List<ListingWithPerson>

    fun getListingsForHealth(): Flow<List<ListingWithPerson>>

    fun getListingsForRepose(): Flow<List<ListingWithPerson>>

    suspend fun deleteListing(listing: ListingWithPerson)
}