package com.example.prayforthem.createlist.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.createlist.domain.CreateListScreenState
import com.example.prayforthem.lists.domain.PersonBasicData
import com.example.prayforthem.names.domain.DignityInteractor
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.names.domain.models.DignityBasicData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateListViewModel(
    private val namesInteractor: NamesInteractor,
    private val dignityInteractor: DignityInteractor
) : ViewModel() {

    private var isForHealth = true
    private var isListFull = false
    private var listTitle = ""
    private var listOfPeople = ArrayList<PersonBasicData>()

    private val screenState = MutableLiveData<CreateListScreenState>(CreateListScreenState.Loading)
    fun getScreenState(): LiveData<CreateListScreenState> = screenState

    private val saveButtonState = MutableLiveData<Boolean>(checkSavingPossibility())
    fun getSaveButtonState(): LiveData<Boolean> = saveButtonState

    init {
        screenState
            .postValue(CreateListScreenState.Content(listOfPeople, listOfPeople.size, isListFull))
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

    private fun addPersonToList(person: PersonBasicData) {
        if (listOfPeople.size < LIST_MAX_SIZE) {
            screenState.postValue(CreateListScreenState.Loading)
            listOfPeople.add(person)
            isListFull = listOfPeople.size >= LIST_MAX_SIZE
            screenState
                .postValue(
                    CreateListScreenState.Content(
                        listOfPeople,
                        listOfPeople.size,
                        isListFull
                    )
                )
            saveButtonState.postValue(checkSavingPossibility())
            Log.d("ADDED PERSON", "$person")
            Log.d("ADDED PERSON", "$listOfPeople")
        }
    }

    fun createNewPerson(dignityId: Int?, nameId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var dignity: DignityBasicData? = null
                val name = namesInteractor.getNameBasicDataById(nameId)
                if (dignityId != null) dignity =
                    dignityInteractor.getDignityBasicDataById(dignityId)
                Log.d("RECEIVED DIG_id VM", "$dignity")
                Log.d("RECEIVED NAME_id VM", "$name")
                addPersonToList(PersonBasicData(dignity, name))
            }
        }

    }

    private fun checkSavingPossibility(): Boolean {
        return (listTitle.isNotEmpty() && listOfPeople.size != ZERO)
    }

    companion object {
        private const val ZERO = 0
        private const val LIST_MAX_SIZE = 10
    }
}