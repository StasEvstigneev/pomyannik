package com.example.prayforthem.prayers.domain.models

data class Prayer(
    val prayerId: Int,
    val title: String,
    val fileName: String,
    val forHealth: Boolean
)