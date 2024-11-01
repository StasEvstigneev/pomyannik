package com.example.prayforthem.utils

import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent
import com.example.prayforthem.storage.domain.GsonJsonConverter
import com.google.gson.Gson

class GsonJsonConverterImpl(private val gson: Gson): GsonJsonConverter {
    override fun getPrayerFromJson(prayerJson: String): PrayerContent {
        return gson.fromJson(prayerJson, PrayerContent::class.java)
    }
}