package com.example.prayforthem.db.converters

import com.example.prayforthem.db.entities.ListingEntity
import com.example.prayforthem.db.models.ListingWithPersonDB
import com.example.prayforthem.db.models.ListingWithTempPersonDB
import com.example.prayforthem.listings.domain.models.Listing
import com.example.prayforthem.listings.domain.models.ListingWithPerson

class ListingDbConverter(
    private val booleanIntDbConverter: BooleanIntDbConverter,
    private val personDbConverter: PersonDbConverter,
    private val personTempDbConverter: PersonTempDbConverter
) {

    fun map(listing: ListingEntity): Listing {
        return Listing(
            listing.listingId,
            listing.title,
            booleanIntDbConverter.map(listing.forHealth)
        )
    }

    fun map(listing: Listing): ListingEntity {
        return ListingEntity(
            listing.listingId,
            listing.title,
            booleanIntDbConverter.map(listing.forHealth)
        )
    }

    fun map(listing: ListingWithPersonDB): ListingWithPerson {
        return ListingWithPerson(
            map(listing.listing),
            listing.personListing.map { person -> personDbConverter.map(person) }
        )
    }

    fun map(listing: ListingWithTempPersonDB): ListingWithPerson {
        return ListingWithPerson(
            map(listing.listing),
            listing.personListing.map { person -> personTempDbConverter.map(person) }
        )
    }
}