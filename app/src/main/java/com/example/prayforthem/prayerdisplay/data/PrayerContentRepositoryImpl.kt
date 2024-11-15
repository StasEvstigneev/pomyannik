package com.example.prayforthem.prayerdisplay.data

import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent
import com.example.prayforthem.prayerdisplay.domain.PrayerContentRepository
import com.example.prayforthem.storage.domain.LocalStorage

class PrayerContentRepositoryImpl(
    private val localStorage: LocalStorage
) : PrayerContentRepository {

    override suspend fun getPrayer(name: String): PrayerContent? {
        return localStorage.getPrayer(name)
    }
}