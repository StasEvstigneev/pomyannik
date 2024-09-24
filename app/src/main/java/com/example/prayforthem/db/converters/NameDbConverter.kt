package com.example.prayforthem.db.converters

import com.example.prayforthem.db.entities.NameEntity
import com.example.prayforthem.db.models.NameBasicDataDB
import com.example.prayforthem.names.domain.Name
import com.example.prayforthem.names.domain.NameBasicData

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

    fun map(name: NameBasicDataDB): NameBasicData {
        return NameBasicData(
            name.nameId,
            name.nameDisplay,
            name.nameGenitive
        )
    }
}