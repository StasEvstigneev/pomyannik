package com.example.prayforthem.db.models

data class NameBasicDataDB(
    val nameId: Int,
    val nameDisplay: String, //для вывода в recyclerview
    val nameGenitive: String //для вывода в карточках о здравии, упокоении
)