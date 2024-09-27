package com.example.prayforthem.names.domain

import com.example.prayforthem.names.domain.models.NameBasicData
import kotlinx.coroutines.flow.Flow

interface NamesRepository {

    fun getNamesBasicData(): Flow<List<NameBasicData>>

}