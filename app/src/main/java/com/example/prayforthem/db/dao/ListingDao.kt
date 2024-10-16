package com.example.prayforthem.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.prayforthem.db.entities.ListingEntity
import com.example.prayforthem.db.models.ListingWithPersonDB

@Dao
interface ListingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListing(listing: ListingEntity): Long

    @Transaction
    @Query("SELECT * FROM listing WHERE listing_id = :id")
    suspend fun getListingById(id: Int): List<ListingWithPersonDB>

}