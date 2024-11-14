package com.example.prayforthem.prayeraddnames.domain

import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.listings.domain.models.PersonDignityName

interface TempPersonInteractor {

    suspend fun addTempPerson(person: Person)

    suspend fun deleteTempPerson(person: Person)

    suspend fun deleteTempPersonByListingId(listingId: Int)

    suspend fun getAllTempPerson(): List<PersonDignityName>

    suspend fun clearAll()
}