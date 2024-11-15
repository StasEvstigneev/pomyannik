package com.example.prayforthem.prayerdisplay.domain

import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent

interface PrayerContentInteractor {

    suspend fun getPrayer(name: String): PrayerContent?

}