package com.example.prayforthem.lists.domain

data class List(
    val id: Int,
    var title: String,
    var people: ArrayList<Person>,// убрать, должна быть сводный Объект ListAndPerson, в котором будут id Person
    val forHealth: Boolean
)