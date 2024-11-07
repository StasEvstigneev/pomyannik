package com.example.prayforthem.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.prayforthem.db.entities.PersonTempEntity
import com.example.prayforthem.db.models.PersonTempDignityNameDB

@Dao
interface TempPersonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPerson(person: PersonTempEntity)

    @Transaction
    @Query("SELECT * FROM person_temp WHERE person_id = :id")
    suspend fun getPersonById(id: Int): PersonTempDignityNameDB

    @Delete
    suspend fun deletePerson(person: PersonTempEntity)

    @Transaction
    @Query("DELETE FROM person_temp WHERE parent_listing_id = :listingId")
    suspend fun deletePersonByListingId(listingId: Int)

}