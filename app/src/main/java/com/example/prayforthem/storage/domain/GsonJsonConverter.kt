package com.example.prayforthem.storage.domain

import com.example.prayforthem.names.domain.models.NameBasicData

interface GsonJsonConverter {

    fun getNameBasicDataFromJson(json: String): NameBasicData

    fun getJsonFromNameBasicData(person: NameBasicData): String


}