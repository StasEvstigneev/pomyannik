package com.example.prayforthem.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer_category")
data class PrayerCategoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "category_id")
    val categoryId: Int,
    @ColumnInfo(name="category_title")
    val categoryTitle: String
)