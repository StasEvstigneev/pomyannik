package com.example.prayforthem.prayeraddnames.data

import com.example.prayforthem.db.AppDatabase
import com.example.prayforthem.db.converters.PersonTempDbConverter
import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.prayeraddnames.domain.TempPersonRepository

class TempPersonRepositoryImpl(
    private val database: AppDatabase,
    private val personTempDbConverter: PersonTempDbConverter

) : TempPersonRepository {

    override suspend fun addTempPerson(person: Person) {
        database
            .tempPersonDao()
            .addPerson(personTempDbConverter.map(person))
    }

    override suspend fun deleteTempPerson(person: Person) {
        database
            .tempPersonDao()
            .deletePerson(personTempDbConverter.map(person))
    }

    override suspend fun deleteTempPersonByListingId(listingId: Int) {
        database
            .tempPersonDao()
            .deletePersonByListingId(listingId)
    }
}