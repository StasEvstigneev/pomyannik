package com.example.prayforthem.db.converters

import com.example.prayforthem.db.entities.DignityEntity
import com.example.prayforthem.names.domain.Dignity

class DignityDbConverter(private val booleanIntDbConverter: BooleanIntDbConverter) {

    fun map(dignity: DignityEntity): Dignity {
        return Dignity(
            dignity.dignityId,
            dignity.dignityDisplay,
            dignity.dignityNominative,
            dignity.dignityGenitive,
            dignity.dignityDative,
            dignity.dignityAccusative,
            dignity.dignityInstrumental,
            dignity.dignityPrepositional,
            dignity.dignityShort,
            booleanIntDbConverter.map(dignity.isChurchTitle)
        )
    }

    fun map(dignity: Dignity): DignityEntity {
        return DignityEntity(
            dignity.id,
            dignity.dignityDisplay,
            dignity.dignityNominative,
            dignity.dignityGenitive,
            dignity.dignityDative,
            dignity.dignityAccusative,
            dignity.dignityInstrumental,
            dignity.dignityPrepositional,
            dignity.dignityShort,
            booleanIntDbConverter.map(dignity.isChurchTitle)
        )
    }
}