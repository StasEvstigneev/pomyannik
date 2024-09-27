package com.example.prayforthem.names.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.names.domain.models.NameBasicData
import com.example.prayforthem.names.domain.models.NamesScreenState
import kotlinx.coroutines.launch

class NamesViewModel(private val namesInteractor: NamesInteractor) : ViewModel() {

    private var namesList = ArrayList<NameBasicData>()
    private var dignityList = arrayListOf(
        "Патриарх",
        "Митрополит",
        "Епископ",
        "Архимандрит",
        "Иерей",
        "Диакон",
        "Монах",
        "Патриарх",
        "Митрополит",
        "Епископ",
        "Архимандрит",
        "Иерей",
        "Диакон",
        "Монах",
        "Патриарх",
        "Митрополит",
        "Епископ",
        "Архимандрит",
        "Иерей",
        "Диакон",
        "Монах",
        "Патриарх",
        "Митрополит",
        "Епископ",
        "Архимандрит",
        "Иерей",
        "Диакон",
        "Монах"
    )

    private val screenState = MutableLiveData<NamesScreenState>(NamesScreenState.Loading)
    fun getScreenState(): LiveData<NamesScreenState> = screenState

    init {
        getNamesList()
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