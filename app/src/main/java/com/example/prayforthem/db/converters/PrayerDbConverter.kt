package com.example.prayforthem.db.converters

import com.example.prayforthem.db.entities.PrayerEntity
import com.example.prayforthem.prayers.domain.models.Prayer

class PrayerDbConverter(
    private val booleanIntDbConverter: BooleanIntDbConverter
) {

    fun map(prayer: PrayerEntity): Prayer {
        return Prayer(
            prayerId = prayer.prayerId,
            title = prayer.title,
            fileName = prayer.fileName,
            forHealth = booleanIntDbConverter.map(prayer.forHealth)
        )
    }

    fun map(prayer: Prayer): PrayerEntity {
        return PrayerEntity(
            prayerId = prayer.prayerId,
            title = prayer.title,
            fileName = prayer.fileName,
            forHealth = booleanIntDbConverter.map(prayer.forHealth)
        )
    }

}