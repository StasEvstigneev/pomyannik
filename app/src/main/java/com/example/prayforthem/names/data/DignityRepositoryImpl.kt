package com.example.prayforthem.names.data

import com.example.prayforthem.db.AppDatabase
import com.example.prayforthem.db.converters.DignityDbConverter
import com.example.prayforthem.db.models.DignityBasicDataDB
import com.example.prayforthem.names.domain.DignityRepository
import com.example.prayforthem.names.domain.models.DignityBasicData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DignityRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val dignityDbConverter: DignityDbConverter
) : DignityRepository {

    override fun getAllBasicDignityData(): Flow<List<DignityBasicData>> = flow {
        val dignityBasicDataList = appDatabase
            .dignityDao()
            .getAllBasicDignityData()

        emit(convertDignityBasicData(dignityBasicDataList))
    }

    override suspend fun getDignityBasicDataById(id: Int): DignityBasicData {
        return dignityDbConverter.map(appDatabase.dignityDao().getDignityBasicDataById(id))
    }

    private fun convertDignityBasicData(list: List<DignityBasicDataDB>): List<DignityBasicData> {
        return list.map { dignity -> dignityDbConverter.map(dignity) }
    }
}