package com.example.prayforthem.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listing")
data class ListingEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "listing_id")
    val listingId: Int? = null,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "for_health")
    val forHealth: Int //0-false, 1-true
)