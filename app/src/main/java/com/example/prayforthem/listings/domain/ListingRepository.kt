package com.example.prayforthem.listings.domain

import com.example.prayforthem.listings.domain.models.Listing
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.domain.models.Person
import kotlinx.coroutines.flow.Flow

interface ListingRepository {

    suspend fun createListing(listing: Listing, personData: List<Pair<Int?, Int>>)

    suspend fun getListingById(id: Int): ListingWithPerson

    fun getListings(isForHealth: Boolean): Flow<List<ListingWithPerson>>

    suspend fun deleteListing(listing: ListingWithPerson)

    suspend fun updateListing(personDel: List<Person>, listing: Listing, personAdd: List<Person>)

    suspend fun getReservedListingById(id: Int): ListingWithPerson

}