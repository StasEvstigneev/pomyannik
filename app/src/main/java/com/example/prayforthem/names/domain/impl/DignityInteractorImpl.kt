package com.example.prayforthem.names.domain.impl

import com.example.prayforthem.names.domain.DignityInteractor
import com.example.prayforthem.names.domain.DignityRepository
import com.example.prayforthem.names.domain.models.Dignity
import com.example.prayforthem.names.domain.models.DignityBasicData
import kotlinx.coroutines.flow.Flow

class DignityInteractorImpl(private val dignityRepository: DignityRepository) : DignityInteractor {

    override fun getAllBasicDignityData(): Flow<List<DignityBasicData>> {
        return dignityRepository.getAllBasicDignityData()
    }

    override suspend fun getDignityBasicDataById(id: Int): DignityBasicData {
        return dignityRepository.getDignityBasicDataById(id)
    }

    override suspend fun getDignityById(id: Int): Dignity {
        return dignityRepository.getDignityById(id)
    }
}