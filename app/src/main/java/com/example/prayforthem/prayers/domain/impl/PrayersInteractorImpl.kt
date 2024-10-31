package com.example.prayforthem.prayers.domain.impl

import com.example.prayforthem.prayers.domain.PrayersInteractor
import com.example.prayforthem.prayers.domain.PrayersRepository
import com.example.prayforthem.prayers.domain.models.CategoryWithPrayers

class PrayersInteractorImpl(private val repository: PrayersRepository) : PrayersInteractor {
    override suspend fun getPrayersList(categoryId: Int): CategoryWithPrayers {
        return repository.getPrayersList(categoryId)
    }
}