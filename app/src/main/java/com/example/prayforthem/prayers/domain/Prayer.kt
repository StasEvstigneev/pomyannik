package com.example.prayforthem.prayers.domain

data class Prayer(
    val id: Int,
    val title: String,
    val headings: List<String>,
    val text: List<String>,
    val fileName: String
)