package com.example.prayforthem.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.prayforthem.db.entities.PersonEntity
import com.example.prayforthem.db.models.PersonDignityNameDB

@Dao
interface PersonDao {

    @Insert(entity = PersonEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPerson(person: PersonEntity)

    @Transaction
    @Query("SELECT * FROM person WHERE person_id = :id")
    suspend fun getPersonById(id: Int): PersonDignityNameDB

    @Transaction
    @Query("SELECT * FROM person WHERE person_id IN (:ids)")
    suspend fun getPersonListByIds(ids: List<Int>): List<PersonDignityNameDB>

    @Delete
    suspend fun deletePerson(person: PersonEntity)

    @Update
    suspend fun updatePerson(person: PersonEntity)

}