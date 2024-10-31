package com.example.prayforthem.db.converters

import com.example.prayforthem.db.models.CategoryWithPrayersDB
import com.example.prayforthem.prayers.domain.models.CategoryWithPrayers

class CategoryPrayersDbConverter(
    private val prayerDbConverter: PrayerDbConverter,
    private val prayerCategoryDbConverter: PrayerCategoryDbConverter
) {

    fun map(category: CategoryWithPrayersDB): CategoryWithPrayers {
        return CategoryWithPrayers(
            prayerCategory = prayerCategoryDbConverter.map(category.prayerCategory),
            prayers = category.prayers.map { item -> prayerDbConverter.map(item) }
        )
    }

    fun map(category: CategoryWithPrayers): CategoryWithPrayersDB {
        return CategoryWithPrayersDB(
            prayerCategory = prayerCategoryDbConverter.map(category.prayerCategory),
            prayers = category.prayers.map { item -> prayerDbConverter.map(item) }
        )
    }

}