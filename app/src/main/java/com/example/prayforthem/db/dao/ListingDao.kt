package com.example.prayforthem.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.prayforthem.db.entities.ListingEntity
import com.example.prayforthem.db.models.ListingWithPersonDB

@Dao
interface ListingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addListing(listing: ListingEntity): Long

    @Transaction
    @Query("SELECT * FROM listing WHERE listing_id = :id")
    suspend fun getListingById(id: Int): ListingWithPersonDB

    @Transaction
    @Query("SELECT * FROM listing WHERE for_health = :isForHealth AND listing_id > 5") // добавил id>5, чтобы исключить резервные списки
    suspend fun getListings(isForHealth: Int): List<ListingWithPersonDB>

    @Transaction
    @Delete
    suspend fun deleteListing(listing: ListingEntity)

    @Update
    suspend fun updateListing(listing: ListingEntity)

}