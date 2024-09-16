package com.example.prayforthem.createlist

import androidx.lifecycle.ViewModel

class CreateListViewModel : ViewModel() {

    private var isForHealth = true

    fun setListType(isForHealth: Boolean) {
        this.isForHealth = isForHealth
    }
}