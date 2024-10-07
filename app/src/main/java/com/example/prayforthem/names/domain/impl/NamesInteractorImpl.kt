package com.example.prayforthem.names.domain.impl

import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.names.domain.NamesRepository
import com.example.prayforthem.names.domain.models.NameBasicData
import kotlinx.coroutines.flow.Flow

class NamesInteractorImpl(private val namesRepository: NamesRepository) : NamesInteractor {

    override fun getNamesBasicData(): Flow<List<NameBasicData>> {
        return namesRepository.getNamesBasicData()
    }

    override suspend fun getNameBasicDataById(id: Int): NameBasicData {
        return namesRepository.getNameBasicDataById(id)
    }
}