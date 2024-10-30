package com.example.prayforthem.prayerscategories.data

import com.example.prayforthem.db.AppDatabase
import com.example.prayforthem.db.converters.PrayerCategoryDbConverter
import com.example.prayforthem.prayerscategories.domain.PrayersCategoriesRepository
import com.example.prayforthem.prayerscategories.domain.models.PrayerCategory

class PrayersCategoriesRepositoryImpl(
    private val database: AppDatabase,
    private val converter: PrayerCategoryDbConverter
) :
    PrayersCategoriesRepository {

    override suspend fun getPrayersCategories(): List<PrayerCategory> {
        return database.prayerCategoryDao().getAllCategories().map { item -> converter.map(item) }
    }
}