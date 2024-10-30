package com.example.prayforthem.prayerscategories.domain.models

sealed class PrayersCategoriesScreenState {

    data object Loading : PrayersCategoriesScreenState()
    data class Content(val list: List<PrayerCategory>) : PrayersCategoriesScreenState()
    data object Error : PrayersCategoriesScreenState()
}