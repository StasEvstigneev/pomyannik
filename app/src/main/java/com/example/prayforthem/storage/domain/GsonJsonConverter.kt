package com.example.prayforthem.storage.domain

import com.example.prayforthem.prayerdisplay.domain.PrayerContent

interface GsonJsonConverter {

    fun getPrayerFromJson(prayerJson: String): PrayerContent

}