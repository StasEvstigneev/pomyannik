package com.example.prayforthem.storage.data

import android.content.SharedPreferences
import com.example.prayforthem.storage.domain.LocalStorage

class LocalStorageImpl(private val sharedPreferences: SharedPreferences): LocalStorage {
    override fun saveStringData(key: String, data: String) {
        TODO("Not yet implemented")
    }

    override fun getSavedStringData(key: String): String {
        TODO("Not yet implemented")
    }
}