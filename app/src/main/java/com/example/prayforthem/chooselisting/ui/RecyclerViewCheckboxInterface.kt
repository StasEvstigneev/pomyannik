package com.example.prayforthem.chooselisting.ui

interface RecyclerViewCheckboxInterface<T> {

    fun onCheckBoxClick(item: T, isChecked: Boolean)
}