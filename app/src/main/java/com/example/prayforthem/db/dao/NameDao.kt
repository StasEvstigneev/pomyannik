package com.example.prayforthem.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.prayforthem.db.entities.NameEntity
import com.example.prayforthem.db.models.NameBasicDataDB

@Dao
interface NameDao {

    @Query("SELECT name_id, name_display, name_gen FROM names ORDER by name_id asc")
    suspend fun getAllBasicNameData(): List<NameBasicDataDB>

    @Query("SELECT name_id, name_display, name_gen FROM names WHERE name_id = :id")
    suspend fun getNameBasicDataById(id: Int): NameBasicDataDB

    @Query("SELECT name_id, name_display, name_gen FROM names WHERE name_id IN (:ids)")
    suspend fun getBasicNameDataByIds(ids: List<Int>): List<NameBasicDataDB>

    @Query("SELECT * FROM names WHERE name_id IN (:ids)")
    suspend fun getNamesByIds(ids: List<Int>): List<NameEntity>

    @Insert(entity = NameEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun addName(name: NameEntity)

}