package com.example.prayforthem.prayeraddnames.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.names.domain.DignityInteractor
import com.example.prayforthem.names.domain.NamesInteractor
import com.example.prayforthem.prayeraddnames.domain.TempPersonInteractor
import com.example.prayforthem.prayeraddnames.domain.models.PrayerAddNamesScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class PrayerAddNamesViewModel(
    private val forHealth: Boolean,
    private val listingInteractor: ListingInteractor,
    private val tempPersonInteractor: TempPersonInteractor,
    private val namesInteractor: NamesInteractor,
    private val dignityInteractor: DignityInteractor
) : ViewModel() {
    private val tempPersonList: ArrayList<PersonDignityName> = arrayListOf()
    private val personFromListings: ArrayList<PersonDignityName> = arrayListOf()

    private val screenState =
        MutableLiveData<PrayerAddNamesScreenState>(PrayerAddNamesScreenState.Loading)

    fun getScreenState(): LiveData<PrayerAddNamesScreenState> = screenState

    init {
        screenState.postValue(PrayerAddNamesScreenState.Content(arrayListOf()))
    }

    fun createNewPerson(dignityId: Int?, nameId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val person = Person(
                    id = null,
                    idDignity = dignityId,
                    idName = nameId,
                    parentListingId = getListingId()
                )
                val dignity =
                    if (dignityId != null) dignityInteractor.getDignityById(dignityId) else null
                val name = namesInteractor.getNameById(nameId)
                val newPersonDignityName = PersonDignityName(
                    person = person,
                    dignity = dignity,
                    name = name
                )
                addNewPerson(newPersonDignityName)
            }
        }
    }

    private fun addNewPerson(newPerson: PersonDignityName) {
        tempPersonList.add(newPerson)
        screenState.postValue(PrayerAddNamesScreenState.Content(tempPersonList))
    }

    fun deleteTempPerson(position: Int) {
        val personToDelete = tempPersonList[position]
        personFromListings.remove(personToDelete)
        tempPersonList.removeAt(position)
        screenState.postValue(PrayerAddNamesScreenState.Content(tempPersonList))
    }

    fun saveTempList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // очистка предыдущих temp_person
                tempPersonInteractor.deleteTempPersonByListingId(getListingId())
                val savedTempPersonList = tempPersonInteractor.getAllTempPerson()
                val listToSave = ArrayList<Person>()
                if (tempPersonList.isNotEmpty()) {
                    var id = savedTempPersonList.size + ONE
                    tempPersonList.forEach { item ->
                        val person = preparePersonToSave(item.person, id)
                        id += ONE
                        listToSave.add(person)
                    }
                }
                tempPersonInteractor.addTempPerson(listToSave)
            }
        }
    }

    fun updateAddedListings(ids: ArrayList<Int>) {
        ids.forEach { id ->
            getListingById(id)
        }
    }

    private fun getListingById(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                processListing(listingInteractor.getListingById(id))
            }
        }
    }

    private fun processListing(listing: ListingWithPerson) {
        listing.personListing.forEach { item ->
            if (!personFromListings.contains(item)) {
                personFromListings.add(item)
                tempPersonList.add(item)
            }
        }
        screenState.postValue(PrayerAddNamesScreenState.Content(tempPersonList))
    }

    private fun preparePersonToSave(person: Person, id: Int): Person {
        return Person(
            id = id,
            idDignity = person.idDignity,
            idName = person.idName,
            parentListingId = getListingId()
        )
    }

    open fun getListingId(): Int {
        return if (forHealth) LIST_HEALTH else LIST_REPOSE
    }

    companion object {
        private const val LIST_HEALTH = 1
        private const val LIST_REPOSE = 2
        private const val ONE = 1
    }

}