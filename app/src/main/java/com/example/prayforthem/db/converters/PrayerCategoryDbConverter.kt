package com.example.prayforthem.db.converters

import com.example.prayforthem.db.entities.PrayerCategoryEntity
import com.example.prayforthem.prayerscategories.domain.PrayerCategory

class PrayerCategoryDbConverter {

    fun map(category: PrayerCategoryEntity): PrayerCategory {
        return PrayerCategory(
            categoryId = category.categoryId,
            categoryTitle = category.categoryTitle
        )
    }

    fun map(category: PrayerCategory): PrayerCategoryEntity {
        return PrayerCategoryEntity(
            categoryId = category.categoryId,
            categoryTitle = category.categoryTitle
        )
    }

}