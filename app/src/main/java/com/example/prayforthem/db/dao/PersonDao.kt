package com.example.prayforthem.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.prayforthem.db.entities.PersonEntity
import com.example.prayforthem.db.models.PersonDignityNameDB

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPerson(person: PersonEntity)

    @Transaction
    @Query("SELECT * FROM person WHERE person_id = :id")
    suspend fun getPersonById(id: Int): PersonDignityNameDB

    @Transaction
    @Query("SELECT * FROM person WHERE person_id IN (:ids)")
    suspend fun getPersonListByIds(ids: List<Int>): List<PersonDignityNameDB>

}