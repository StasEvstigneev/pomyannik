package com.example.prayforthem.listings.domain

interface RecyclerViewDeleteItem<T> {

    fun onDeleteElementClick(item: T)

}