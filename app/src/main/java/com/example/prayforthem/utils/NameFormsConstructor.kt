package com.example.prayforthem.utils

import com.example.prayforthem.listings.domain.models.PersonBasicData
import com.example.prayforthem.listings.domain.models.PersonDignityName

internal object NameFormsConstructor {

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

    private fun createPersonNominative(person: PersonDignityName): String {
        var result = person.name.nameNominative
        if (person.dignity != null) result = person.dignity.dignityNominative + SPACE + result
        return result
    }

    private fun createPersonGenitive(person: PersonDignityName): String {
        var result = person.name.nameGenitive
        if (person.dignity != null) result = person.dignity.dignityGenitive + SPACE + result
        return result
    }

    private fun createPersonDative(person: PersonDignityName): String {
        var result = person.name.nameDative
        if (person.dignity != null) result = person.dignity.dignityDative + SPACE + result
        return result
    }

    private fun createPersonAccusative(person: PersonDignityName): String {
        var result = person.name.nameAccusative
        if (person.dignity != null) result = person.dignity.dignityAccusative + SPACE + result
        return result
    }

    private fun createPersonInstrumental(person: PersonDignityName): String {
        var result = person.name.nameInstrumental
        if (person.dignity != null) result = person.dignity.dignityInstrumental + SPACE + result
        return result
    }

    private fun createPersonPrepositional(person: PersonDignityName): String {
        var result = person.name.namePrepositional
        if (person.dignity != null) result = person.dignity.dignityPrepositional + SPACE + result
        return result
    }

    fun preparePersonListForPrayer(
        personListing: List<PersonDignityName>,
        form: NameForms
    ): String {
        val result = ArrayList<String>()
        personListing.forEach { item ->
            result.add(
                when (form) {
                    NameForms.NAME_NOMINATIVE -> createPersonNominative(item)
                    NameForms.NAME_GENITIVE -> createPersonGenitive(item)
                    NameForms.NAME_DATIVE -> createPersonDative(item)
                    NameForms.NAME_ACCUSATIVE -> createPersonAccusative(item)
                    NameForms.NAME_INSTRUMENTAL -> createPersonInstrumental(item)
                    NameForms.NAME_PREPOSITIONAL -> createPersonPrepositional(item)
                }
            )
        }
        return result.joinToString(
            separator = ", ",
            prefix = "<b>",
            postfix = "</b>"
        )
    }

    private const val SPACE = " "

}