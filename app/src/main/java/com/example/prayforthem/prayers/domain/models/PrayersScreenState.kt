package com.example.prayforthem.prayers.domain.models

sealed class PrayersScreenState {
    data object Loading : PrayersScreenState()
    data class Content(val categoryName: String, val list: List<Prayer>) : PrayersScreenState()
    data object Error : PrayersScreenState()
}