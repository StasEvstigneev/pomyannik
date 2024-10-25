package com.example.prayforthem.db.converters

import com.example.prayforthem.db.entities.PrayerEntity
import com.example.prayforthem.prayerdisplay.domain.Prayer

class PrayerDbConverter(
    private val booleanIntDbConverter: BooleanIntDbConverter
) {

    fun map(prayer: PrayerEntity): Prayer {
        return Prayer(
            prayerId = prayer.prayerId,
            title = prayer.title,
            fileName = prayer.fileName,
            withNames = booleanIntDbConverter.map(prayer.withNames)
        )
    }

    fun map(prayer: Prayer): PrayerEntity {
        return PrayerEntity(
            prayerId = prayer.prayerId,
            title = prayer.title,
            fileName = prayer.fileName,
            withNames = booleanIntDbConverter.map(prayer.withNames)
        )
    }

}