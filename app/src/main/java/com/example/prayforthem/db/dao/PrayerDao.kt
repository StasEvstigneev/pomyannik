package com.example.prayforthem.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.prayforthem.db.models.CategoryWithPrayersDB

@Dao
interface PrayerDao {

    @Transaction
    @Query("SELECT * FROM prayer_category WHERE category_id = :id")
    fun getPrayers(id: Int): List<CategoryWithPrayersDB>
}