package com.example.prayforthem.utils

interface RecyclerViewClickInterface<T> {

    fun onItemClick(item: T)

    fun onTrashBinClick(position: Int)
}