package com.example.prayforthem.listings.domain.impl

import com.example.prayforthem.listings.domain.PersonInteractor
import com.example.prayforthem.listings.domain.PersonRepository
import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.listings.domain.models.PersonDignityName

class PersonInteractorImpl(private val personRepository: PersonRepository): PersonInteractor {

    override suspend fun savePerson(person: Person) {
        personRepository.savePerson(person)
    }

    override suspend fun getPersonById(id: Int): PersonDignityName {
        return personRepository.getPersonById(id)
    }
}