package com.example.prayforthem.prayers.domain

data class CategoryWithPrayers(
    val prayerCategory: PrayerCategory,
    val prayers: List<Prayer>
)