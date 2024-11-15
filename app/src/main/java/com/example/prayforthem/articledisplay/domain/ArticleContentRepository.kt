package com.example.prayforthem.articledisplay.domain

import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent

interface ArticleContentRepository {

    fun getArticle(fileName: String): PrayerContent?
}