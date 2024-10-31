package com.example.prayforthem.prayers.data

import com.example.prayforthem.db.AppDatabase
import com.example.prayforthem.db.converters.CategoryPrayersDbConverter
import com.example.prayforthem.prayers.domain.PrayersRepository
import com.example.prayforthem.prayers.domain.models.CategoryWithPrayers

class PrayersRepositoryImpl(
    private val database: AppDatabase,
    private val converter: CategoryPrayersDbConverter
) : PrayersRepository {
    override suspend fun getPrayersList(categoryId: Int): CategoryWithPrayers {
        return converter.map(database.prayerDao().getPrayers(categoryId))
    }
}