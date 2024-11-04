package com.example.prayforthem.utils

import com.example.prayforthem.prayerdisplay.domain.PrayerFormatter
import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent

class PrayerFormatterImpl : PrayerFormatter {
    private fun formatHeading(heading: String): String {
        return "<b>$heading</b><br><br>"
    }

    private fun formatParagraph(paragraph: String): String {
        return "$paragraph<br><br>"
    }

    override fun composePrayer(prayer: PrayerContent): String {
        val joinedText = prayer.headings.zip(prayer.paragraphs)
        var prayerText = ""
        joinedText.forEach { pair ->
            prayerText += if (pair.first == null) formatParagraph(pair.second) else formatHeading(
                pair.first!!
            ) + formatParagraph(pair.second)
        }
        return prayerText
    }
}