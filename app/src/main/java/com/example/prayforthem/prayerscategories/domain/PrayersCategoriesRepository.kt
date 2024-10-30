package com.example.prayforthem.prayerscategories.domain

import com.example.prayforthem.prayerscategories.domain.models.PrayerCategory

interface PrayersCategoriesRepository {

    suspend fun getPrayersCategories(): List<PrayerCategory>

}