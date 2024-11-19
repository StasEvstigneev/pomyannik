package com.example.prayforthem.editname.domain

import com.example.prayforthem.names.domain.models.Name

object NameTempNameConverter {

    fun map(name: Name): TempName {
        return TempName(
            id = name.id,
            nameDisplay = name.nameDisplay,
            nameNominative = name.nameNominative,
            nameGenitive = name.nameGenitive,
            nameDative = name.nameDative,
            nameAccusative = name.nameAccusative,
            nameInstrumental = name.nameInstrumental,
            namePrepositional = name.namePrepositional,
            isCustom = name.isCustom
        )
    }

    fun map(name: TempName): Name {
        return Name(
            id = name.id,
            nameDisplay = name.nameDisplay ?: "",
            nameNominative = name.nameNominative ?: "",
            nameGenitive = name.nameGenitive ?: "",
            nameDative = name.nameDative ?: "",
            nameAccusative = name.nameAccusative ?: "",
            nameInstrumental = name.nameInstrumental ?: "",
            namePrepositional = name.namePrepositional ?: "",
            isCustom = name.isCustom
        )
    }

}