package com.example.prayforthem.prayerdisplay.domain.models

data class PrayerContent(
    val title: String,
    val headings: List<String?>,
    val paragraphs: List<String>
)