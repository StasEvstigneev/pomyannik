package com.example.prayforthem.listings.data

import com.example.prayforthem.db.AppDatabase
import com.example.prayforthem.db.converters.PersonDbConverter
import com.example.prayforthem.listings.domain.PersonRepository
import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.listings.domain.models.PersonDignityName

class PersonRepositoryImpl(
    private val database: AppDatabase,
    private val personDbConverter: PersonDbConverter
) : PersonRepository {

    override suspend fun savePerson(person: Person) {
        database
            .personDao()
            .addPerson(
                personDbConverter.map(person)
            )
    }

    override suspend fun getPersonById(id: Int): PersonDignityName {
        return personDbConverter
            .map(database.personDao().getPersonById(id))
    }
}