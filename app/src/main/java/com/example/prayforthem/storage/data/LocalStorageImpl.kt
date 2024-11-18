package com.example.prayforthem.storage.data

import android.content.Context
import android.content.SharedPreferences
import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent
import com.example.prayforthem.storage.domain.GsonJsonConverter
import com.example.prayforthem.storage.domain.LocalStorage

class LocalStorageImpl(
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val gsonJsonConverter: GsonJsonConverter
) : LocalStorage {

    override fun getPrayer(name: String): PrayerContent? {
        try {
            val inputSteam = context.assets.open("prayers/$name.json")
            val json = inputSteam.bufferedReader().use { it.readText() }
            return gsonJsonConverter.getPrayerFromJson(json)
        } catch (e: Exception) {
            return null
        }
    }

    override fun getArticle(name: String): PrayerContent? {
        try {
            val inputSteam = context.assets.open("articles/$name.json")
            val json = inputSteam.bufferedReader().use { it.readText() }
            return gsonJsonConverter.getPrayerFromJson(json)
        } catch (e: Exception) {
            return null
        }
    }

    override fun getThemeSettings(key: String): Int {
        return sharedPreferences.getInt(key, DEFAULT_THEME_CODE)
    }

    override fun saveThemeSettings(key: String, themeCode: Int) {
        sharedPreferences.edit().putInt(key, themeCode).apply()
    }

    companion object {
        private const val DEFAULT_THEME_CODE = 0
    }

}