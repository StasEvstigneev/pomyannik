package com.example.prayforthem.names.data

import com.example.prayforthem.db.AppDatabase
import com.example.prayforthem.db.converters.NameDbConverter
import com.example.prayforthem.db.models.NameBasicDataDB
import com.example.prayforthem.names.domain.NamesRepository
import com.example.prayforthem.names.domain.models.NameBasicData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NamesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val nameDbConverter: NameDbConverter
) : NamesRepository {

    override fun getNamesBasicData(): Flow<List<NameBasicData>> = flow {
        val namesBasicDataList = appDatabase.nameDao().getAllBasicNameData()
        emit(convertNamesBasicData(namesBasicDataList))
    }

    override suspend fun getNameBasicDataById(id: Int): NameBasicData {
        return nameDbConverter.map(appDatabase.nameDao().getNameBasicDataById(id))
    }

    private fun convertNamesBasicData(names: List<NameBasicDataDB>): List<NameBasicData> {
        return names.map { name -> nameDbConverter.map(name) }
    }

}