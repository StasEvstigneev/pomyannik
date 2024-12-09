package com.example.prayforthem.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.prayforthem.db.entities.NameEntity
import com.example.prayforthem.db.models.NameBasicDataDB

@Dao
interface NameDao {

    @Query("SELECT name_id, name_display, name_gen FROM names ORDER by name_id asc")
    suspend fun getAllBasicNameData(): List<NameBasicDataDB>

    @Query("SELECT name_id, name_display, name_gen FROM names WHERE name_id = :id")
    suspend fun getNameBasicDataById(id: Int): NameBasicDataDB

    @Query("SELECT * FROM names WHERE name_id = :id")
    suspend fun getNameById(id: Int): NameEntity

    @Insert(entity = NameEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun addName(name: NameEntity)

    @Query("SELECT * FROM names WHERE is_custom = 1")
    suspend fun getCustomNames(): List<NameEntity>

    @Delete
    suspend fun deleteName(name: NameEntity)

    @Update(entity = NameEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCustomName(name: NameEntity)

}