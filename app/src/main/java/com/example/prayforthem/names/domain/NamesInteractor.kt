package com.example.prayforthem.names.domain

import com.example.prayforthem.names.domain.models.NameBasicData
import kotlinx.coroutines.flow.Flow

interface NamesInteractor {

    fun getNamesBasicData(): Flow<List<NameBasicData>>

    suspend fun getNameBasicDataById(id: Int): NameBasicData

}