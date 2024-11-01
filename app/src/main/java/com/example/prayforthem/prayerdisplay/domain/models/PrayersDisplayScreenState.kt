package com.example.prayforthem.prayerdisplay.domain.models

sealed class PrayersDisplayScreenState {
    data object Loading : PrayersDisplayScreenState()
    data class Content(
        val title: String,
        val text: String
    ) : PrayersDisplayScreenState()

    data object Error : PrayersDisplayScreenState()
}