package com.example.prayforthem.prayerdisplay.domain

import com.example.prayforthem.prayerdisplay.domain.models.PrayerContent

interface PrayerFormatter {

    fun composePrayer(prayer: PrayerContent): String

}