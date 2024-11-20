package com.example.prayforthem.listingdisplay.domain

interface SharingInteractor {

    fun shareListAsText(title: String, names: List<String>)

}