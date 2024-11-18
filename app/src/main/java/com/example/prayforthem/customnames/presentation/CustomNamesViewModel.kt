package com.example.prayforthem.customnames.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.customnames.domain.CustomNamesScreenState
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.names.domain.models.Name
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomNamesViewModel(
    private val namesInteractor: NamesInteractor
) : ViewModel() {

    private val screenState =
        MutableLiveData<CustomNamesScreenState>(CustomNamesScreenState.Loading)

    fun getScreenState(): LiveData<CustomNamesScreenState> = screenState

    init {
        screenState.postValue(CustomNamesScreenState.Loading)
        getCustomNames()
    }

    fun getCustomNames() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                processList(namesInteractor.getCustomNames())
            }
        }
    }

    fun deleteName(name: Name) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                namesInteractor.deleteCustomName(name)
                processList(namesInteractor.getCustomNames())
            }
        }
    }

    private fun processList(list: List<Name>) {
        if (list.isNotEmpty()) {
            screenState.postValue(CustomNamesScreenState.Content(list))
        } else {
            screenState.postValue(CustomNamesScreenState.Error)
        }
    }


}