package com.example.prayforthem.articledisplay.domain.impl

import com.example.prayforthem.articledisplay.domain.ArticleContentInteractor
import com.example.prayforthem.articledisplay.domain.ArticleContentRepository
import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent

class ArticleContentInteractorImpl(private val repository: ArticleContentRepository) :
    ArticleContentInteractor {
    override fun getArticle(fileName: String): PrayerContent? {
        return repository.getArticle(fileName)
    }
}