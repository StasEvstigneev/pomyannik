package com.example.prayforthem.prayerdisplay.impl

import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent
import com.example.prayforthem.prayerdisplay.domain.PrayerContentInteractor
import com.example.prayforthem.prayerdisplay.domain.PrayerContentRepository

class PrayerContentInteractorImpl(
    private val repository: PrayerContentRepository
) : PrayerContentInteractor {

    override suspend fun getPrayer(name: String): PrayerContent {
        return repository.getPrayer(name)
    }
}