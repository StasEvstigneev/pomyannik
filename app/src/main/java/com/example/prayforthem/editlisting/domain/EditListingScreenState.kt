package com.example.prayforthem.editlisting.domain

import com.example.prayforthem.listings.domain.models.PersonDignityName

sealed class EditListingScreenState {
    data object Loading : EditListingScreenState()

    data class InitialContent(
        val title: String,
        val list: List<PersonDignityName>,
        val isListFull: Boolean
    ) : EditListingScreenState()

    data class UpdatedContent(
        val list: List<PersonDignityName>,
        val isListFull: Boolean
    ) : EditListingScreenState()
}