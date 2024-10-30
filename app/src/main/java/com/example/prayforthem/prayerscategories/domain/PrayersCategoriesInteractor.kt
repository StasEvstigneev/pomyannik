package com.example.prayforthem.prayerscategories.domain

import com.example.prayforthem.prayerscategories.domain.models.PrayerCategory

interface PrayersCategoriesInteractor {

    suspend fun getPrayersCategories(): List<PrayerCategory>

}