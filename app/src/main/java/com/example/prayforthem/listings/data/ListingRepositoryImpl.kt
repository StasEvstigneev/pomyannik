package com.example.prayforthem.listings.data

import com.example.prayforthem.db.AppDatabase
import com.example.prayforthem.db.converters.BooleanIntDbConverter
import com.example.prayforthem.db.converters.ListingDbConverter
import com.example.prayforthem.db.models.ListingWithPersonDB
import com.example.prayforthem.listings.domain.ListingRepository
import com.example.prayforthem.listings.domain.models.Listing
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListingRepositoryImpl(
    private val database: AppDatabase,
    private val listingDbConverter: ListingDbConverter,
    private val booleanIntDbConverter: BooleanIntDbConverter
) : ListingRepository {
    override suspend fun saveListing(listing: Listing): Long {
        return database.listingDao().addListing(listingDbConverter.map(listing))
    }

    override suspend fun getListingById(id: Int): List<ListingWithPerson> {
        return convertListing(database.listingDao().getListingById(id))
    }

    override fun getListings(isForHealth: Boolean): Flow<List<ListingWithPerson>> = flow {
        val listing = database.listingDao().getListings(booleanIntDbConverter.map(isForHealth))
        emit(convertListing(listing))
    }

    override suspend fun deleteListing(listing: ListingWithPerson) {
        database.listingDao().deleteListing(listingDbConverter.map(listing.listing))
    }

    private fun convertListing(listing: List<ListingWithPersonDB>): List<ListingWithPerson> {
        return listing.map { item -> listingDbConverter.map(item) }
    }
}