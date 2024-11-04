package com.example.prayforthem.prayerdisplay.presentation

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.prayerdisplay.domain.PrayerContentInteractor
import com.example.prayforthem.prayerdisplay.domain.PrayerFormatter
import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent
import com.example.prayforthem.prayerdisplay.domain.models.PrayersDisplayScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrayerDisplayViewModel(
    private val prayerFileName: String,
    private val prayerContentInteractor: PrayerContentInteractor,
    private val prayerFormatter: PrayerFormatter
) : ViewModel() {

    private val screenState =
        MutableLiveData<PrayersDisplayScreenState>(PrayersDisplayScreenState.Loading)

    fun getScreenState(): LiveData<PrayersDisplayScreenState> = screenState

    init {
        screenState.postValue(PrayersDisplayScreenState.Loading)
        getPrayerContent()
    }

    private fun getPrayerContent() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                processPrayer(prayer = prayerContentInteractor.getPrayer(prayerFileName))

            }
        }
    }

    private fun processPrayer(prayer: PrayerContent) {
        val prayerText = prayerFormatter.composePrayer(prayer)

        //добавить шаг с вставкой имен

        val processedPrayer = Html.fromHtml(prayerText, FROM_HTML_MODE_LEGACY)
        screenState.postValue(
            PrayersDisplayScreenState
                .Content(title = prayer.title, text = processedPrayer)
        )
    }

}