package com.example.prayforthem.prayers.domain

import com.example.prayforthem.prayers.domain.models.CategoryWithPrayers

interface PrayersInteractor {

    suspend fun getPrayersList(categoryId: Int): CategoryWithPrayers

}