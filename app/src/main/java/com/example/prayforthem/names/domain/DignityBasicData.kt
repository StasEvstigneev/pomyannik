package com.example.prayforthem.names.domain

data class DignityBasicData(
    val dignityId: Int,
    val dignityDisplay: String, //для вывода в recyclerview
    val dignityShort: String //для вывода в списках
)