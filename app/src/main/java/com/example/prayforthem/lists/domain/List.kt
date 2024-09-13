package com.example.prayforthem.lists.domain

data class List(
    val id: Int,
    var title: String,
    var people: ArrayList<Person>,
    val forHealth: Boolean
)