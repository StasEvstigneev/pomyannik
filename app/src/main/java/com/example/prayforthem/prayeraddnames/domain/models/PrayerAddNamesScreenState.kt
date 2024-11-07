package com.example.prayforthem.prayeraddnames.domain.models

import com.example.prayforthem.listings.domain.models.PersonDignityName

sealed class PrayerAddNamesScreenState {

    data object Loading : PrayerAddNamesScreenState()
    data class Content(val list: List<PersonDignityName>) : PrayerAddNamesScreenState()
}