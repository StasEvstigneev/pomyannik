package com.example.prayforthem.db.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.prayforthem.db.entities.ListingEntity
import com.example.prayforthem.db.entities.PersonEntity

data class ListingWithPersonDB(
    @Embedded val listing: ListingEntity,
    @Relation(
        entity = PersonEntity::class,
        parentColumn = "listing_id",
        entityColumn = "parent_listing_id",

    )
    val personListing: List<PersonDignityNameDB>
)