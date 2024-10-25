package com.example.prayforthem.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.prayforthem.db.entities.PrayerCategoryEntity

@Dao
interface PrayerCategoryDao {

    @Query("SELECT * FROM prayer_category")
    suspend fun getAllCategories(): List<PrayerCategoryEntity>

}