package com.example.prayforthem.listings.domain

import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.listings.domain.models.PersonDignityName

interface PersonRepository {

    suspend fun savePerson(person: Person)

    suspend fun getPersonById(id: Int): PersonDignityName
}