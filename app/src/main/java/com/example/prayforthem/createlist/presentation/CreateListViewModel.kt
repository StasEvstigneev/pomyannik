package com.example.prayforthem.createlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prayforthem.createlist.domain.CreateListScreenState
import com.example.prayforthem.lists.domain.Person

class CreateListViewModel : ViewModel() {

    private var isForHealth = true
    private var list = arrayListOf<Person>()

    private val screenState = MutableLiveData<CreateListScreenState>(CreateListScreenState.Loading)
    fun getScreenState(): LiveData<CreateListScreenState> = screenState

    init {
        screenState.postValue(CreateListScreenState.Loading)
    }

    fun setListType(isForHealth: Boolean) {
        this.isForHealth = isForHealth
    }

    fun uploadList() {
        val uploadedList = getList()


    }

    private fun getList(): ArrayList<Person> {
        return list
    }
}