package com.example.prayforthem.lists.domain

import com.example.prayforthem.names.Dignity
import com.example.prayforthem.names.Name

data class Person(
    val id: Int,
    val dignity: Dignity?,
    val name: Name
)