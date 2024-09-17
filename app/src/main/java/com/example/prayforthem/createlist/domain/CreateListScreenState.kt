package com.example.prayforthem.createlist.domain

import com.example.prayforthem.lists.domain.Person

sealed class CreateListScreenState() {
    data object Loading : CreateListScreenState()
    data class Content(val list: ArrayList<Person>) : CreateListScreenState()

}