package com.example.prayforthem.lists.domain

import com.example.prayforthem.names.domain.models.Dignity
import com.example.prayforthem.names.domain.models.Name

data class Person(
    val dignity: Dignity? = null,
    val name: Name
)