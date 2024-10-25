package com.example.prayforthem.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "prayer_category_crossref", primaryKeys = ["category_id", "prayer_id"])
data class PrayerCategoryPrayerCrossRef(
    @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name = "prayer_id")
    val prayerId: Int
)