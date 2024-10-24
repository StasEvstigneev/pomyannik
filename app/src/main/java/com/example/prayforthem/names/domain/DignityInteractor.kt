package com.example.prayforthem.names.domain

import com.example.prayforthem.names.domain.models.Dignity
import com.example.prayforthem.names.domain.models.DignityBasicData
import kotlinx.coroutines.flow.Flow

interface DignityInteractor {

    fun getAllBasicDignityData(): Flow<List<DignityBasicData>>

    suspend fun getDignityBasicDataById(id: Int): DignityBasicData

    suspend fun getDignityById(id: Int): Dignity

}