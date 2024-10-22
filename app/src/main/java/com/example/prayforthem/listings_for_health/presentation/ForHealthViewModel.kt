package com.example.prayforthem.listings_for_health.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.PersonInteractor
import com.example.prayforthem.listings.domain.models.ListingScreenState
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class ForHealthViewModel(
    private val listingInteractor: ListingInteractor,
    private val personInteractor: PersonInteractor,
    private val isForHealth: Boolean
) : ViewModel() {

    private val screenState = MutableLiveData<ListingScreenState>(ListingScreenState.Loading)
    fun getScreenState(): LiveData<ListingScreenState> = screenState

    init {
        screenState.postValue(ListingScreenState.Loading)
        getListings()
    }

    fun getListings() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                listingInteractor
                    .getListings(isForHealth)
                    .collect { listing ->
                        processListing(listing)
                    }
            }
        }

    }

    fun deleteListing(listing: ListingWithPerson) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                listing.personListing.forEach { item ->
                    personInteractor.deletePerson(item.person)
                }
                listingInteractor.deleteListing(listing)
                listingInteractor
                    .getListings(isForHealth)
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