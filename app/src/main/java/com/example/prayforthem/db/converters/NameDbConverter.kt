package com.example.prayforthem.db.converters

import com.example.prayforthem.db.entities.NameEntity
import com.example.prayforthem.names.domain.Name

class NameDbConverter(private val booleanIntDbConverter: BooleanIntDbConverter) {

    fun map(name: Name): NameEntity {
        return NameEntity(
            name.id,
            name.nameDisplay,
            name.nameNominative,
            name.nameGenitive,
            name.nameDative,
            name.nameAccusative,
            name.nameInstrumental,
            name.namePrepositional,
            booleanIntDbConverter.map(name.isCustom)
        )
    }

    fun map(name: NameEntity): Name {
        return Name(
            name.nameId,
            name.nameDisplay,
            name.nameNominative,
            name.nameGenitive,
            name.nameDative,
            name.nameAccusative,
            name.nameInstrumental,
            name.namePrepositional,
            booleanIntDbConverter.map(name.isCustom)
        )
    }
}