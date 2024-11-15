package com.example.prayforthem.articledisplay.data

import com.example.prayforthem.articledisplay.domain.ArticleContentRepository
import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent
import com.example.prayforthem.storage.domain.LocalStorage

class ArticleContentRepositoryImpl(private val localStorage: LocalStorage) :
    ArticleContentRepository {
    override fun getArticle(fileName: String): PrayerContent? {
        return localStorage.getArticle(fileName)
    }
}