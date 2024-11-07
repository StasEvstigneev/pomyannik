package com.example.prayforthem.prayeraddnames.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.domain.models.Person
import com.example.prayforthem.prayeraddnames.domain.TempPersonInteractor
import com.example.prayforthem.prayeraddnames.domain.models.PrayerAddNamesScreenState
import com.example.prayforthem.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrayerAddNamesViewModel(
    private val prayerFileName: String,
    private val forHealth: Boolean,
    private val listingInteractor: ListingInteractor,
    private val tempPersonInteractor: TempPersonInteractor //ЗАМЕНИТЬ на intractor для таблицы person_temp
) : ViewModel() {

    private val screenState =
        MutableLiveData<PrayerAddNamesScreenState>(PrayerAddNamesScreenState.Loading)

    fun getScreenState(): LiveData<PrayerAddNamesScreenState> = screenState

    init {
        getListing()
    }

    fun createNewPerson(dignityId: Int?, nameId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val newPerson = Person(
                    id = null,
                    idDignity = dignityId,
                    idName = nameId,
                    parentListingId = if (forHealth) Constants.LIST_HEALTH else Constants.LIST_REPOSE
                )
                tempPersonInteractor.addTempPerson(newPerson)
            }
        }

    }

    fun getListing() {
        screenState.postValue(PrayerAddNamesScreenState.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val list = listingInteractor
                    .getReservedListingById(if (forHealth) LIST_HEALTH else LIST_REPOSE)
                processListing(list)
            }
        }
    }

    private fun processListing(list: ListingWithPerson) {
        screenState.postValue(PrayerAddNamesScreenState.Content(list.personListing))

    }

    companion object {
        private const val LIST_HEALTH = 1
        private const val LIST_REPOSE = 2
    }

}