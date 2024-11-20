package com.example.prayforthem.listingdisplay.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.listingdisplay.domain.SharingInteractor
import com.example.prayforthem.listingdisplay.domain.models.ListingDisplayScreenState
import com.example.prayforthem.listings.domain.ListingInteractor
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.utils.NameFormsConstructor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListingDisplayViewModel(
    private val isForHealth: Boolean,
    private val listingId: Int,
    private val listingInteractor: ListingInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {

    private val screenState =
        MutableLiveData<ListingDisplayScreenState>(ListingDisplayScreenState.Loading)

    fun getScreenState(): LiveData<ListingDisplayScreenState> = screenState

    init {
        screenState.postValue(ListingDisplayScreenState.Loading)
        getListing()
    }

    fun getListing() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val listing = listingInteractor.getListingById(listingId)
                screenState.postValue(
                    ListingDisplayScreenState
                        .Content(
                            listingTitle = listing.listing.title,
                            list = listing.personListing
                        )
                )
            }
        }
    }

    fun shareListingAsText(title: String, names: List<PersonDignityName>) {
        val namesList: ArrayList<String> = arrayListOf()
        names.forEach { item ->
            namesList.add(NameFormsConstructor.createPersonShortGenitive(item))
        }
        sharingInteractor.shareListAsText(title, namesList)
    }

}