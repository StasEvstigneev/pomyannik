package com.example.prayforthem.prayers.domain

data class Prayer(
    val id: Int,
    val headings: List<String>,
    val text: List<String>
)