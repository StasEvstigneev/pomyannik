package com.example.prayforthem.listings.data

import com.example.prayforthem.db.AppDatabase
import com.example.prayforthem.db.converters.BooleanIntDbConverter
import com.example.prayforthem.db.converters.ListingDbConverter
import com.example.prayforthem.db.converters.PersonDbConverter
import com.example.prayforthem.db.entities.PersonEntity
import com.example.prayforthem.db.models.ListingWithPersonDB
import com.example.prayforthem.listings.domain.ListingRepository
import com.example.prayforthem.listings.domain.models.Listing
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.domain.models.Person
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListingRepositoryImpl(
    private val database: AppDatabase,
    private val listingDbConverter: ListingDbConverter,
    private val booleanIntDbConverter: BooleanIntDbConverter,
    private val personDbConverter: PersonDbConverter
) : ListingRepository {
    override suspend fun createListing(listing: Listing, personData: List<Pair<Int?, Int>>) {
        return database.listingDao().createListing(listingDbConverter.map(listing), personData)
    }

    override suspend fun getListingById(id: Int): ListingWithPerson {
        return listingDbConverter.map(database.listingDao().getListingById(id))
    }

    override fun getListings(isForHealth: Boolean): Flow<List<ListingWithPerson>> = flow {
        val listing = database.listingDao().getListings(booleanIntDbConverter.map(isForHealth))
        emit(convertListing(listing))
    }

    override suspend fun deleteListing(listing: ListingWithPerson) {
        val personDel = ArrayList<PersonEntity>()
        listing.personListing.forEach { item ->
            personDel.add(personDbConverter.map(item.person))
        }
        database
            .listingDao()
            .deleteListingData(
                personDel = personDel,
                listing = listingDbConverter.map(listing.listing)
            )
    }

    override suspend fun updateListing(
        personDel: List<Person>,
        listing: Listing,
        personAdd: List<Person>
    ) {
        database
            .listingDao()
            .updateListingData(
                personDel = convertPersonList(personDel),
                listing = listingDbConverter.map(listing),
                personAdd = convertPersonList(personAdd)
            )
    }

    override suspend fun getReservedListingById(id: Int): ListingWithPerson {
        return listingDbConverter.map(database.listingDao().getReservedListingById(id))
    }

    private fun convertListing(listing: List<ListingWithPersonDB>): List<ListingWithPerson> {
        return listing.map { item -> listingDbConverter.map(item) }
    }

    private fun convertPersonList(list: List<Person>): List<PersonEntity> {
        return list.map { item -> personDbConverter.map(item) }
    }
}