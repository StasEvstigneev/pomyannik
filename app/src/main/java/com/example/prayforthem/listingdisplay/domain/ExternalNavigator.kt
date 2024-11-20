package com.example.prayforthem.listingdisplay.domain

interface ExternalNavigator {

    fun shareListAsText(title: String, names: List<String>)

}