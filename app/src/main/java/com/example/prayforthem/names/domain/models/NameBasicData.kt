package com.example.prayforthem.names.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NameBasicData(
    val nameId: Int,
    val nameDisplay: String, //для вывода в recyclerview
    val nameGenitive: String //для вывода в карточках о здравии, упокоении
) : Parcelable {
    override fun toString(): String {
        return nameDisplay
    }
}