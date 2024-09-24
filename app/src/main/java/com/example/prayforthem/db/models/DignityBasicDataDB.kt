package com.example.prayforthem.db.models

data class DignityBasicDataDB(
    val dignityId: Int,
    val dignityDisplay: String, //для вывода в recyclerview
    val dignityShort: String //для вывода в списках
)