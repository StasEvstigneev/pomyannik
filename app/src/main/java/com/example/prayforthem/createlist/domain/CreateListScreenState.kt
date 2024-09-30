package com.example.prayforthem.createlist.domain

import com.example.prayforthem.names.domain.models.PersonBasicData

sealed class CreateListScreenState() {
    data object Loading : CreateListScreenState()
    data class Content(
        val list: ArrayList<PersonBasicData>,
        val listSize: Int,
        val isListFull: Boolean
    ) : CreateListScreenState()

}