package com.example.prayforthem.prayers.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.prayers.domain.PrayersInteractor
import com.example.prayforthem.prayers.domain.models.CategoryWithPrayers
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
                processPrayers(prayersInteractor.getPrayersList(categoryId))
            }
        }
    }

    private fun processPrayers(category: CategoryWithPrayers) {
        if (category.prayers.isNotEmpty()) {
            screenState.postValue(
                PrayersScreenState.Content(
                    category.prayerCategory.categoryTitle,
                    category.prayers
                )
            )
        } else {
            screenState.postValue(PrayersScreenState.Error)
        }
    }
}