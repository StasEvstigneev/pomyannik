package com.example.prayforthem.storage.domain

interface LocalStorage {

    fun saveStringData(key: String, data: String)

    fun getSavedStringData(key: String): String

}