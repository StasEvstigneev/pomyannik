package com.example.prayforthem.editlisting.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.editlisting.domain.EditListingScreenState
import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.models.Listing
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.names.domain.DignityInteractor
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditListingViewModel(
    private val listingId: Int,
    private val listingInteractor: ListingInteractor,
    private val dignityInteractor: DignityInteractor,
    private val namesInteractor: NamesInteractor
) : ViewModel() {

    private lateinit var initialListing: Listing
    private var initialTitle: String = ""
    private var updatedTitle: String = ""
    private val initialList = arrayListOf<PersonDignityName>()
    private val updatedList = arrayListOf<PersonDignityName>()
    private val deletedPerson = arrayListOf<PersonDignityName>()

    private val screenState =
        MutableLiveData<EditListingScreenState>(EditListingScreenState.Loading)

    fun getScreenState(): LiveData<EditListingScreenState> = screenState

    private val saveButtonState =
        MutableLiveData<Boolean>(checkSavingPossibility() && !checkDataIdentity())

    fun getSaveButtonState(): LiveData<Boolean> = saveButtonState

    private val exitDialogStatus = MutableLiveData<Boolean>(!checkDataIdentity())
    fun getExitDialogStatus(): LiveData<Boolean> = exitDialogStatus

    init {
        getListing()
    }

    private fun getListing() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val listingWithPerson = listingInteractor.getListingById(listingId)
                processListing(listingWithPerson)
            }
        }
    }

    private fun processListing(listingWithPerson: ListingWithPerson) {
        initialListing = listingWithPerson.listing
        initialTitle = listingWithPerson.listing.title
        updatedTitle = listingWithPerson.listing.title
        initialList.addAll(listingWithPerson.personListing)
        updatedList.addAll(listingWithPerson.personListing)
        screenState.postValue(
            EditListingScreenState.InitialContent(
                updatedTitle,
                initialList,
                isListFull(initialList)
            )
        )
    }

    fun addPersonToList(dignityId: Int?, nameId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val newPersonDignityName = PersonDignityName(
                    person = Person(
                        id = null,
                        idDignity = dignityId,
                        idName = nameId,
                        parentListingId = listingId
                    ),
                    dignity = if (dignityId != null) dignityInteractor
                        .getDignityById(dignityId) else null,
                    name = namesInteractor.getNameById(nameId)
                )
                updatedList.add(newPersonDignityName)
                if (deletedPerson.contains(newPersonDignityName)) deletedPerson.remove(
                    newPersonDignityName
                )
                updateScreenState()
            }
        }

    }

    fun deleteItemFromList(item: PersonDignityName) {
        if (item.person.id != null) deletedPerson.add(item)
        updatedList.remove(item)
        updateScreenState()
    }

    fun updateTitle(title: String) {
        updatedTitle = title
        saveButtonState.postValue(checkSavingPossibility() && !checkDataIdentity())
        exitDialogStatus.postValue(!checkDataIdentity())
    }

    private fun updateScreenState() {
        screenState
            .postValue(
                EditListingScreenState
                    .UpdatedContent(
                        list = updatedList,
                        isListFull = isListFull(updatedList)
                    )
            )
        saveButtonState.postValue(checkSavingPossibility() && !checkDataIdentity())
        exitDialogStatus.postValue(!checkDataIdentity())
    }

    fun updateListing() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val personDel = ArrayList<Person>()
                val personAdd = ArrayList<Person>()
                val listing = Listing(
                    listingId = listingId,
                    title = updatedTitle,
                    forHealth = initialListing.forHealth
                )
                deletedPerson.forEach { item -> personDel.add(item.person) }
                updatedList.forEach { item -> personAdd.add(item.person) }
                listingInteractor.updateListing(personDel, listing, personAdd)
            }
        }
    }

    private fun checkSavingPossibility(): Boolean {
        return (updatedTitle.isNotEmpty() && updatedList.size != Constants.ZERO)
    }

    private fun checkDataIdentity(): Boolean {
        return (updatedTitle == initialTitle && updatedList == initialList)
    }

    private fun isListFull(list: List<PersonDignityName>): Boolean {
        return list.size >= Constants.LIST_MAX_SIZE
    }

}