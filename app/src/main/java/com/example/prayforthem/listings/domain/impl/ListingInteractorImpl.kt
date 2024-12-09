package com.example.prayforthem.listings.domain.impl

import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.ListingRepository
import com.example.prayforthem.listings.domain.models.Listing
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.domain.models.Person
import kotlinx.coroutines.flow.Flow

class ListingInteractorImpl(private val repository: ListingRepository) : ListingInteractor {
    override suspend fun createListing(listing: Listing, personData: List<Pair<Int?, Int>>) {
        return repository.createListing(listing, personData)
    }

    override suspend fun getListingById(id: Int): ListingWithPerson {
        return repository.getListingById(id)
    }

    override fun getListings(isForHealth: Boolean): Flow<List<ListingWithPerson>> {
        return repository.getListings(isForHealth)
    }

    override suspend fun deleteListing(listing: ListingWithPerson) {
        repository.deleteListing(listing)
    }

    override suspend fun updateListing(
        personDel: List<Person>,
        listing: Listing,
        personAdd: List<Person>
    ) {
        repository.updateListing(personDel, listing, personAdd)
    }

    override suspend fun getReservedListingById(id: Int): ListingWithPerson {
        return repository.getReservedListingById(id)
    }

}