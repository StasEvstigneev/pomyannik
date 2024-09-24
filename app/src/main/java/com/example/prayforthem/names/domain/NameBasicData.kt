package com.example.prayforthem.names.domain

data class NameBasicData(
    val nameId: Int,
    val nameDisplay: String, //для вывода в recyclerview
    val nameGenitive: String //для вывода в карточках о здравии, упокоении
)