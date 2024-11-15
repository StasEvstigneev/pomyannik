package com.example.prayforthem.storage.domain

import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent

interface LocalStorage {

    fun getPrayer(name: String): PrayerContent?

    fun getArticle(name: String): PrayerContent?

}