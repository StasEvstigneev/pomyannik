package com.example.prayforthem.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prayer")
data class PrayerEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "prayer_id")
    val prayerId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "file_name")
    val fileName: String,
    @ColumnInfo(name = "with_names")
    val withNames: Int //0-false, 1-true
)