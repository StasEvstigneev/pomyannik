package com.example.prayforthem.prayers.domain.models

import com.example.prayforthem.prayerscategories.domain.models.PrayerCategory

data class CategoryWithPrayers(
    val prayerCategory: PrayerCategory,
    val prayers: List<Prayer>
)