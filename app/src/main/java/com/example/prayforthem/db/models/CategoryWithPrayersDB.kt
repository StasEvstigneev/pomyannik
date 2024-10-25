package com.example.prayforthem.db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.prayforthem.db.entities.PrayerCategoryEntity
import com.example.prayforthem.db.entities.PrayerCategoryPrayerCrossRef
import com.example.prayforthem.db.entities.PrayerEntity

data class CategoryWithPrayersDB(
    @Embedded val prayerCategory: PrayerCategoryEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "prayer_id",
        associateBy = Junction(PrayerCategoryPrayerCrossRef::class)
    )
    val prayers: List<PrayerEntity>
)