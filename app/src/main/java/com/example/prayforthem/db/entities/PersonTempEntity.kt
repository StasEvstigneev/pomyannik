package com.example.prayforthem.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "person_temp",
    foreignKeys = [
        ForeignKey(
            entity = DignityEntity::class,
            parentColumns = ["dignity_id"],
            childColumns = ["id_dignity"]
        ),
        ForeignKey(
            entity = NameEntity::class,
            parentColumns = ["name_id"],
            childColumns = ["id_name"]
        )
    ],
    indices = [Index(value = ["id_dignity"]), Index(value = ["id_name"])]
)
data class PersonTempEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("person_id")
    val personId: Int?,
    @ColumnInfo("id_dignity")
    val idDignity: Int?,
    @ColumnInfo("id_name")
    val idName: Int,
    @ColumnInfo("parent_listing_id")
    val parentListingId: Int
)