package com.example.prayforthem.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.prayforthem.db.entities.DignityEntity
import com.example.prayforthem.db.models.DignityBasicDataDB

@Dao
interface DignityDao {

    @Query("SELECT dignity_id, dignity_display, dignity_short FROM dignity ORDER by dignity_id asc")
    suspend fun getAllBasicDignityData(): List<DignityBasicDataDB>

    @Query("SELECT dignity_id, dignity_display, dignity_short FROM dignity WHERE dignity_id = :id")
    suspend fun getDignityBasicDataById(id: Int): DignityBasicDataDB

    @Query("SELECT * FROM dignity WHERE dignity_id = :id")
    suspend fun getDignityById(id: Int): DignityEntity

}