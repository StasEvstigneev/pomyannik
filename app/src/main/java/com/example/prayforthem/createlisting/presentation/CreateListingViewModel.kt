package com.example.prayforthem.createlisting.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.createlisting.domain.CreateListScreenState
import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.PersonInteractor
import com.example.prayforthem.listings.domain.models.Listing
import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.listings.domain.models.PersonBasicData
import com.example.prayforthem.names.domain.DignityInteractor
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.names.domain.models.DignityBasicData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateListingViewModel(
    private val isForHealth: Boolean,
    private val namesInteractor: NamesInteractor,
    private val dignityInteractor: DignityInteractor,
    private val personInteractor: PersonInteractor,
    private val listingInteractor: ListingInteractor
) : ViewModel() {

    private var isListFull = false
    private var listTitle = ""
    private var listOfPeople = ArrayList<PersonBasicData>()

    private val screenState = MutableLiveData<CreateListScreenState>(CreateListScreenState.Loading)
    fun getScreenState(): LiveData<CreateListScreenState> = screenState

    private val saveButtonState = MutableLiveData<Boolean>(checkSavingPossibility())
    fun getSaveButtonState(): LiveData<Boolean> = saveButtonState

    private val exitDialogStatus = MutableLiveData<Boolean>(false)
    fun getExitDialogStatus(): LiveData<Boolean> = exitDialogStatus

    init {
        screenState
            .postValue(CreateListScreenState.Content(listOfPeople, listOfPeople.size, isListFull))
        exitDialogStatus.postValue(listTitle.isNotEmpty() || listOfPeople.size > ZERO)
    }

    fun updateListTitle(title: String) {
        Log.d("TITLE", "Title before method = $listTitle")
        listTitle = title
        saveButtonState.postValue(checkSavingPossibility())
        Log.d("TITLE", "Title after method = $listTitle")
        exitDialogStatus.postValue(listTitle.isNotEmpty() || listOfPeople.size > ZERO)
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
            exitDialogStatus.postValue(listTitle.isNotEmpty() || listOfPeople.size > ZERO)
            Log.d("CREATE LISTING", "Добавили: $person")
            Log.d("CREATE LISTING", "Список после добавления: $listOfPeople")
        }
    }

    fun createNewPersonBasicData(dignityId: Int?, nameId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dignity: DignityBasicData? =
                    if (dignityId != null) dignityInteractor.getDignityBasicDataById(dignityId) else null
                val name = namesInteractor.getNameBasicDataById(nameId)
                addPersonToList(PersonBasicData(dignity, name))
            }
        }

    }

    fun removePersonFromList(position: Int) {
        Log.d("CREATE LISTING", "Удаляем: ${listOfPeople[position]}")
        listOfPeople.removeAt(position)
        screenState.postValue(
            CreateListScreenState.Content(
                listOfPeople,
                listOfPeople.size,
                listOfPeople.size >= LIST_MAX_SIZE
            )
        )
        Log.d("CREATE LISTING", "Список после удаления: $listOfPeople")
        saveButtonState.postValue(checkSavingPossibility())
        exitDialogStatus.postValue(listTitle.isNotEmpty() || listOfPeople.size > ZERO)
    }

    fun saveList() {
        if (listOfPeople.size > ZERO && listTitle.isNotEmpty()) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    val listingId: Int = listingInteractor
                        .saveListing(
                            Listing(
                                listingId = null,
                                title = listTitle,
                                forHealth = isForHealth
                            )
                        ).toInt()
                    Log.d("CREATE LISTING", "Создали список: $listTitle, c ID: $listingId")

                    listOfPeople.forEach { person ->
                        personInteractor.savePerson(
                            Person(
                                id = null,
                                idDignity = person.dignity?.dignityId,
                                idName = person.name.nameId,
                                parentListingId = listingId
                            )
                        )
                        Log.d("CREATE LISTING", "Добавили в базу: $person")
                    }
                }
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