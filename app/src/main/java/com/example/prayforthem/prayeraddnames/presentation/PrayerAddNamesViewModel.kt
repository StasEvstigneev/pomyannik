package com.example.prayforthem.prayeraddnames.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.prayeraddnames.domain.PrayerAddNamesScreenState

class PrayerAddNamesViewModel(
    private val prayerFileName: String,
    private val forHealth: Boolean,
) : ViewModel() {

    private val screenState =
        MutableLiveData<PrayerAddNamesScreenState>(PrayerAddNamesScreenState.Loading)

    fun getScreenState(): LiveData<PrayerAddNamesScreenState> = screenState

    private val personList: ArrayList<PersonDignityName> = arrayListOf()

    init {
        screenState.postValue(PrayerAddNamesScreenState.Content(personList))
    }


}