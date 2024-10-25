package com.example.prayforthem.prayerdisplay.domain

data class PrayerContent(
    val title: String,
    val headings: List<String>,
    val paragraphs: List<String>
)