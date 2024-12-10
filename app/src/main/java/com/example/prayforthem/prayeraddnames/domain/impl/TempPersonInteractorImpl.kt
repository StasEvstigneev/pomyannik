package com.example.prayforthem.prayeraddnames.domain.impl

import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.prayeraddnames.domain.TempPersonInteractor
import com.example.prayforthem.prayeraddnames.domain.TempPersonRepository

class TempPersonInteractorImpl(
    private val repository: TempPersonRepository
) : TempPersonInteractor {

    override suspend fun addTempPerson(list: List<Person>) {
        repository.addTempPerson(list)
    }

    override suspend fun deleteTempPerson(person: Person) {
        repository.deleteTempPerson(person)
    }

    override suspend fun deleteTempPersonByListingId(listingId: Int) {
        repository.deleteTempPersonByListingId(listingId)
    }

    override suspend fun getAllTempPerson(): List<PersonDignityName> {
        return repository.getAllTempPerson()
    }

    override suspend fun clearAll() {
        repository.clearAll()
    }
}