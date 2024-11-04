package com.example.prayforthem.prayerdisplay.domain.models

import android.text.Spanned

sealed class PrayersDisplayScreenState {
    data object Loading : PrayersDisplayScreenState()
    data class Content(
        val title: String,
        val text: Spanned
    ) : PrayersDisplayScreenState()

    data object Error : PrayersDisplayScreenState()
}