package com.example.prayforthem.prayers.domain

import com.example.prayforthem.prayerdisplay.domain.Prayer
import com.example.prayforthem.prayerscategories.domain.PrayerCategory

data class CategoryWithPrayers(
    val prayerCategory: PrayerCategory,
    val prayers: List<Prayer>
)