package com.example.prayforthem.prayerdisplay.domain

data class Prayer(
    val prayerId: Int,
    val title: String,
    val fileName: String,
    val withNames: Boolean
)