package com.example.prayforthem.listings

interface RecyclerViewClickInterface<T> {

    fun onItemClick(item: T)
    fun onDeleteElementClick(item: T)
}