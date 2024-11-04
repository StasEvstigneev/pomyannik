package com.example.prayforthem.prayerdisplay.domain

import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent

interface PrayerFormatter {

    fun formatHeading(heading: String): String

    fun formatParagraph(paragraph: String): String

    fun composePrayer(prayer: PrayerContent): String

}