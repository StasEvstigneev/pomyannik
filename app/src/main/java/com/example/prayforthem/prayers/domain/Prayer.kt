package com.example.prayforthem.prayers.domain

data class Prayer(
    val prayerId: Int,
    val title: String,
    val fileName: String,
    val withNames: Boolean
)