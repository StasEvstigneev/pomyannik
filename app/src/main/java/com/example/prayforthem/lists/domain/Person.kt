package com.example.prayforthem.lists.domain

import com.example.prayforthem.names.Dignity
import com.example.prayforthem.names.Name

data class Person(
    val id: Int,
    var dignity: Dignity? = null,
    var name: Name
)