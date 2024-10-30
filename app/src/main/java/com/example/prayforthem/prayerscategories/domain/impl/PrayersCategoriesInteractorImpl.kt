package com.example.prayforthem.prayerscategories.domain.impl

import com.example.prayforthem.prayerscategories.domain.PrayersCategoriesInteractor
import com.example.prayforthem.prayerscategories.domain.PrayersCategoriesRepository
import com.example.prayforthem.prayerscategories.domain.models.PrayerCategory

class PrayersCategoriesInteractorImpl(private val repository: PrayersCategoriesRepository) :
    PrayersCategoriesInteractor {

    override suspend fun getPrayersCategories(): List<PrayerCategory> {
        return repository.getPrayersCategories()
    }
}