package com.example.prayforthem.prayers.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.prayers.domain.PrayersInteractor
import com.example.prayforthem.prayers.domain.models.Prayer
import com.example.prayforthem.prayers.domain.models.PrayersScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrayersViewModel(
    private val categoryId: Int,
    private val prayersInteractor: PrayersInteractor
) : ViewModel() {

    private val screenState = MutableLiveData<PrayersScreenState>(PrayersScreenState.Loading)
    fun getScreenState(): LiveData<PrayersScreenState> = screenState

    init {
        screenState.postValue(PrayersScreenState.Loading)
        getPrayers()
    }

    private fun getPrayers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                processPrayers(prayersInteractor.getPrayersList(categoryId).prayers)
            }
        }
    }

    private fun processPrayers(prayers: List<Prayer>) {
        if (prayers.isNotEmpty()) {
            screenState.postValue(PrayersScreenState.Content(prayers))
        } else {
            screenState.postValue(PrayersScreenState.Error)
        }
    }
}