package com.example.prayforthem.chooselisting.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.chooselisting.domain.ChooseListingScreenState
import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChooseListingViewModel(
    private val isForHealth: Boolean,
    private val listingsInteractor: ListingInteractor
) : ViewModel() {

    private val screenState =
        MutableLiveData<ChooseListingScreenState>(ChooseListingScreenState.Loading)

    fun getScreenState(): LiveData<ChooseListingScreenState> = screenState

    private val chosenListingIds: ArrayList<Int> = arrayListOf()

    private val chosenListings = MutableLiveData<ArrayList<Int>>(chosenListingIds)
    fun getChosenListings(): LiveData<ArrayList<Int>> = chosenListings

    init {
        screenState.postValue(ChooseListingScreenState.Loading)
        getListings()
    }

    private fun getListings() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                listingsInteractor
                    .getListings(isForHealth)
                    .collect { listing ->
                        processListing(listing)
                    }
            }
        }
    }

    private fun processListing(listing: List<ListingWithPerson>) {
        if (listing.isNotEmpty()) {
            screenState.postValue(ChooseListingScreenState.Content(listing))
        } else {
            screenState.postValue(ChooseListingScreenState.Placeholder)
        }

    }

    fun processCheckBoxClick(id: Int, isChecked: Boolean) {
        if (isChecked) {
            addListingId(id)
        } else {
            deleteListingId(id)
        }
        Log.d("LISTING_ID", "Список добавленных id: $chosenListingIds")
    }

    private fun addListingId(id: Int) {
        chosenListingIds.add(id)
        chosenListings.postValue(chosenListingIds)
    }

    private fun deleteListingId(id: Int) {
        chosenListingIds.remove(id)
        chosenListings.postValue(chosenListingIds)
    }


}