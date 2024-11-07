package com.example.prayforthem.prayeraddnames.domain

import com.example.prayforthem.listings.domain.models.Person

interface TempPersonInteractor {

    suspend fun addTempPerson(person: Person)

    suspend fun deleteTempPerson(person: Person)

    suspend fun deleteTempPersonByListingId(listingId: Int)
}