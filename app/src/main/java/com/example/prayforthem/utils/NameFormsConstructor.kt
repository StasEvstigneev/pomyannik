package com.example.prayforthem.utils

import com.example.prayforthem.listings.domain.models.PersonBasicData
import com.example.prayforthem.listings.domain.models.PersonDignityName

object NameFormsConstructor {

    fun createPersonDisplay(person: PersonBasicData): String {
        var result = person.name.nameDisplay
        if (person.dignity != null) result = person.dignity.dignityDisplay + SPACE + result
        return result
    }

    fun createPersonDisplay(person: PersonDignityName): String {
        var result = person.name.nameDisplay
        if (person.dignity != null) result = person.dignity.dignityDisplay + SPACE + result
        return result
    }


    fun createPersonShortGenitive(person: PersonDignityName): String {
        var result = person.name.nameGenitive
        if (person.dignity != null) result = person.dignity.dignityShort + SPACE + result
        return result
    }

    private const val SPACE = " "

}