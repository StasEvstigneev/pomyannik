package com.example.prayforthem.lists.domain

import com.example.prayforthem.names.domain.models.Dignity
import com.example.prayforthem.names.domain.models.Name

//Должен быть cross-reference class см. тему Nested Relationships
data class Person(
    val id: Int? = null,
    val dignity: Dignity? = null,
    val name: Name,
    val listId: Int
)