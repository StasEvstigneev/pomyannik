package com.example.prayforthem.names.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.names.domain.DignityInteractor
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.names.domain.models.DignityBasicData
import com.example.prayforthem.names.domain.models.NameBasicData
import com.example.prayforthem.names.domain.models.NamesScreenState
import kotlinx.coroutines.launch

class NamesViewModel(
    private val namesInteractor: NamesInteractor,
    private val dignityInteractor: DignityInteractor
) : ViewModel() {

    private var namesList = ArrayList<NameBasicData>()
    private val dignityList = ArrayList<DignityBasicData>()

    private val screenState = MutableLiveData<NamesScreenState>(NamesScreenState.Loading)
    fun getScreenState(): LiveData<NamesScreenState> = screenState

    init {
        viewModelScope.launch {
            dignityInteractor
                .getAllBasicDignityData()
                .collect { dignity ->
                    dignity.forEach { item -> dignityList.add(item) }
                }
            namesInteractor
                .getNamesBasicData()
                .collect { names ->
                    processNamesBasicData(names)
                }

        }
    }

    fun getNamesList() {
        viewModelScope.launch {
            namesInteractor
                .getNamesBasicData()
                .collect { names ->
                    processNamesBasicData(names)
                }
        }
    }
    
    private fun processNamesBasicData(updatedNamesList: List<NameBasicData>) {
        if (updatedNamesList.isNotEmpty()) {
            namesList = updatedNamesList as ArrayList<NameBasicData>
            screenState.postValue(NamesScreenState.Default(dignityList, namesList))
        }

    }

}