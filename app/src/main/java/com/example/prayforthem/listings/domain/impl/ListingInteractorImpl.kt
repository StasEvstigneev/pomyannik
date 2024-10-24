package com.example.prayforthem.listings.domain.impl

import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.ListingRepository
import com.example.prayforthem.listings.domain.models.Listing
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import kotlinx.coroutines.flow.Flow

class ListingInteractorImpl(private val repository: ListingRepository) : ListingInteractor {
    override suspend fun saveListing(listing: Listing): Long {
        return repository.saveListing(listing)
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

    override suspend fun updateListing(listing: Listing) {
        repository.updateListing(listing)
    }

}