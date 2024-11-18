package com.example.prayforthem.storage.domain

import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent

interface LocalStorage {

    fun getPrayer(name: String): PrayerContent?

    fun getArticle(name: String): PrayerContent?

    fun getThemeSettings(key: String): Int

    fun saveThemeSettings(key: String, themeCode: Int)

}