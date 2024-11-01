package com.example.prayforthem.prayerdisplay.presentation

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.prayerdisplay.domain.PrayerContentInteractor
import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent
import com.example.prayforthem.prayerdisplay.domain.models.PrayersDisplayScreenState
import com.example.prayforthem.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PrayerDisplayViewModel(
    private val prayerFileName: String,
    private val prayerContentInteractor: PrayerContentInteractor
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
        val textList = prayer.headings.zip(prayer.paragraphs)
        var prayerText = ""
        textList.forEach { pair ->
            prayerText = prayerText + (pair.first ?: "") + pair.second
        }
        //тест замены имени
        val prayerTextNames = prayerText.replace(Constants.PATRIARH, "Кирилла")

        val styledText: Spanned = Html.fromHtml(prayerTextNames, FROM_HTML_MODE_LEGACY)
        screenState.postValue(
            PrayersDisplayScreenState
                .Content(title = prayer.title, text = styledText.toString())
        )

    }

}