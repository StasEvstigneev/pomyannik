package com.example.prayforthem.createlisting.domain

import com.example.prayforthem.listings.domain.models.PersonBasicData

sealed class CreateListScreenState() {
    data object Loading : CreateListScreenState()
    data class Content(
        val list: ArrayList<PersonBasicData>,
        val listSize: Int,
        val isListFull: Boolean
    ) : CreateListScreenState()

}