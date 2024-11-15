package com.example.prayforthem.articledisplay.domain

import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent

interface ArticleContentInteractor {

    fun getArticle(fileName: String): PrayerContent?
}