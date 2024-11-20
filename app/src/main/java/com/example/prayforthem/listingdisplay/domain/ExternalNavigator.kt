package com.example.prayforthem.listingdisplay.domain

import android.net.Uri

interface ExternalNavigator {

    fun shareListAsText(title: String, names: List<String>)

    fun shareListAsJpeg(image: Uri)

}