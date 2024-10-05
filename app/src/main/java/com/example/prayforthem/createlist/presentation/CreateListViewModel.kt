package com.example.prayforthem.createlist.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prayforthem.createlist.domain.CreateListScreenState
import com.example.prayforthem.lists.domain.PersonBasicData

class CreateListViewModel : ViewModel() {

    private var isForHealth = true
    private var isListFull = false
    private var listTitle = ""
    private var list = ArrayList<PersonBasicData>()

    private val screenState = MutableLiveData<CreateListScreenState>(CreateListScreenState.Loading)
    fun getScreenState(): LiveData<CreateListScreenState> = screenState

    private val saveButtonState = MutableLiveData<Boolean>(checkSavingPossibility())
    fun getSaveButtonState(): LiveData<Boolean> = saveButtonState

    init {
        screenState
            .postValue(CreateListScreenState.Content(list, list.size, isListFull))
    }

    fun setListType(isForHealth: Boolean) {
        this.isForHealth = isForHealth
    }

    fun updateListTitle(title: String) {
        Log.d("TITLE", "Title before method = $listTitle")
        listTitle = title
        saveButtonState.postValue(checkSavingPossibility())
        Log.d("TITLE", "Title after method = $listTitle")
    }

    fun addPersonToList(person: PersonBasicData) {
        if (list.size < LIST_MAX_SIZE) {
            list.add(person)
            isListFull = list.size >= LIST_MAX_SIZE
            screenState
                .postValue(CreateListScreenState.Content(list, list.size, isListFull))
            saveButtonState.postValue(checkSavingPossibility())
            Log.d("ADDED PERSON", "$person")
            Log.d("ADDED PERSON", "$list")
        }
    }

    private fun checkSavingPossibility(): Boolean {
        return (listTitle.isNotEmpty() && list.size != ZERO)
    }

    companion object {
        private const val ZERO = 0
        private const val LIST_MAX_SIZE = 10
    }
}