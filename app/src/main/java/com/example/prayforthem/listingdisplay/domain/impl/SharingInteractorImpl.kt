package com.example.prayforthem.listingdisplay.domain.impl

import com.example.prayforthem.listingdisplay.domain.ExternalNavigator
import com.example.prayforthem.listingdisplay.domain.SharingInteractor

class SharingInteractorImpl(private val externalNavigator: ExternalNavigator) : SharingInteractor {
    override fun shareListAsText(title: String, names: List<String>) {
        externalNavigator.shareListAsText(title, names)
    }
}