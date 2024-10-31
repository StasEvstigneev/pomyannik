package com.example.prayforthem.prayers.domain

import com.example.prayforthem.prayers.domain.models.CategoryWithPrayers

interface PrayersRepository {

    suspend fun getPrayersList(categoryId: Int): CategoryWithPrayers

}