package com.example.prayforthem.db.converters

import com.example.prayforthem.db.entities.PersonEntity
import com.example.prayforthem.db.models.PersonDignityNameDB
import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.listings.domain.models.PersonDignityName

class PersonDbConverter(
    private val nameDbConverter: NameDbConverter,
    private val dignityDbConverter: DignityDbConverter
) {

    fun map(person: PersonEntity): Person {
        return Person(
            person.personId,
            person.idDignity,
            person.idName,
            person.parentListingId
        )
    }

    fun map(person: Person): PersonEntity {
        return PersonEntity(
            person.id,
            person.idDignity,
            person.idName,
            person.parentListingId
        )
    }

    fun map(person: PersonDignityNameDB): PersonDignityName {
        return PersonDignityName(
            map(person.personEntity),
            if (person.dignity != null) dignityDbConverter.map(person.dignity) else null,
            nameDbConverter.map(person.name)
        )
    }

    fun map(person: PersonDignityName): PersonDignityNameDB {
        return PersonDignityNameDB(
            map(person.person),
            if (person.dignity != null) dignityDbConverter.map(person.dignity) else null,
            nameDbConverter.map(person.name)
        )
    }

}