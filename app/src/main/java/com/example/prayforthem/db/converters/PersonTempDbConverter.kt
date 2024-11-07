package com.example.prayforthem.db.converters

import com.example.prayforthem.db.entities.PersonTempEntity
import com.example.prayforthem.db.models.PersonTempDignityNameDB
import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.listings.domain.models.PersonDignityName

class PersonTempDbConverter(
    private val nameDbConverter: NameDbConverter,
    private val dignityDbConverter: DignityDbConverter
) {

    fun map(person: PersonTempEntity): Person {
        return Person(
            person.personId,
            person.idDignity,
            person.idName,
            person.parentListingId
        )
    }

    fun map(person: Person): PersonTempEntity {
        return PersonTempEntity(
            person.id,
            person.idDignity,
            person.idName,
            person.parentListingId
        )
    }

    fun map(person: PersonTempDignityNameDB): PersonDignityName {
        return PersonDignityName(
            map(person.personTempEntity),
            if (person.dignity != null) dignityDbConverter.map(person.dignity) else null,
            nameDbConverter.map(person.name)
        )
    }

    fun map(person: PersonDignityName): PersonTempDignityNameDB {
        return PersonTempDignityNameDB(
            map(person.person),
            if (person.dignity != null) dignityDbConverter.map(person.dignity) else null,
            nameDbConverter.map(person.name)
        )
    }
}