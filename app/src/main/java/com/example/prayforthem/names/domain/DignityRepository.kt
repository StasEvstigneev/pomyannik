package com.example.prayforthem.names.domain

import com.example.prayforthem.names.domain.models.DignityBasicData
import kotlinx.coroutines.flow.Flow

interface DignityRepository {

    fun getAllBasicDignityData(): Flow<List<DignityBasicData>>

}