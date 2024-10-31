package com.example.prayforthem.prayerscategories.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.prayerscategories.domain.PrayersCategoriesInteractor
import com.example.prayforthem.prayerscategories.domain.models.PrayerCategory
import com.example.prayforthem.prayerscategories.domain.models.PrayersCategoriesScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class PrayersCategoriesViewModel(
    private val prayersCategoriesInteractor: PrayersCategoriesInteractor
) : ViewModel() {

    private val screenState =
        MutableLiveData<PrayersCategoriesScreenState>(PrayersCategoriesScreenState.Loading)

    fun getScreenState(): LiveData<PrayersCategoriesScreenState> = screenState

    init {
        screenState.postValue(PrayersCategoriesScreenState.Loading)
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                processCategories(prayersCategoriesInteractor.getPrayersCategories())
            }
        }
    }

    private fun processCategories(categories: List<PrayerCategory>) {
        if (categories.isNotEmpty()) {
            screenState.postValue(PrayersCategoriesScreenState.Content(categories))
        } else {
            screenState.postValue(PrayersCategoriesScreenState.Error)
        }
    }
}