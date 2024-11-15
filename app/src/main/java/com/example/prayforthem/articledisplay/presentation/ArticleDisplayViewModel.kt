package com.example.prayforthem.articledisplay.presentation

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prayforthem.articledisplay.domain.ArticleContentInteractor
import com.example.prayforthem.prayerdisplay.domain.PrayerFormatter
import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent
import com.example.prayforthem.prayerdisplay.domain.models.PrayersDisplayScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ArticleDisplayViewModel(
    private val articleFileName: String,
    private val articleContentInteractor: ArticleContentInteractor,
    private val prayerFormatter: PrayerFormatter,
) : ViewModel() {

    private val screenState =
        MutableLiveData<PrayersDisplayScreenState>(PrayersDisplayScreenState.Loading)

    fun getScreenState(): LiveData<PrayersDisplayScreenState> = screenState

    init {
        screenState.postValue(PrayersDisplayScreenState.Loading)
        prepareContent()
    }

    private fun prepareContent() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                processArticle(
                    article = articleContentInteractor.getArticle(articleFileName)
                )
            }
        }
    }

    private fun processArticle(article: PrayerContent?) {
        if (article != null) {
            val articleTitle = article.title
            val articleText = prayerFormatter.composePrayer(article)
            val processedArticle = Html.fromHtml(articleText, FROM_HTML_MODE_LEGACY)
            screenState.postValue(
                PrayersDisplayScreenState.Content(
                    title = articleTitle,
                    text = processedArticle
                )
            )
        } else {
            screenState.postValue(PrayersDisplayScreenState.Error)
        }
    }

}