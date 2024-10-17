package com.example.prayforthem.listings_for_health.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.models.ListingScreenState
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForHealthViewModel(
    private val listingInteractor: ListingInteractor
) : ViewModel() {

    private val screenState = MutableLiveData<ListingScreenState>(ListingScreenState.Loading)
    fun getScreenState(): LiveData<ListingScreenState> = screenState

    init {
        getListingsForHealth()
    }

    fun getListingsForHealth() {
        screenState.postValue(ListingScreenState.Loading)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                listingInteractor
                    .getListingsForHealth()
                    .collect { listing ->
                        processListing(listing)
                    }
            }
        }

    }

    fun deleteListing(listing: ListingWithPerson) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                listingInteractor.deleteListing(listing)
                listingInteractor
                    .getListingsForHealth()
                    .collect { listing ->
                        processListing(listing)
                    }
            }
        }
    }

    private fun processListing(listing: List<ListingWithPerson>) {
        if (listing.isNotEmpty()) {
            screenState.postValue(ListingScreenState.Content(listing))
        } else {
            screenState.postValue(ListingScreenState.Placeholder)
        }

    }

}