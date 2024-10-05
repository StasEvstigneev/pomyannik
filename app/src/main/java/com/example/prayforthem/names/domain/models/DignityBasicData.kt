package com.example.prayforthem.names.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DignityBasicData(
    val dignityId: Int,
    val dignityDisplay: String, //для вывода в recyclerview
    val dignityShort: String //для вывода в списках
) : Parcelable {
    override fun toString(): String {
        return dignityDisplay
    }
}