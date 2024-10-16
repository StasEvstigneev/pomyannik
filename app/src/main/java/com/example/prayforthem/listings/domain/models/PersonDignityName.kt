package com.example.prayforthem.listings.domain.models

import com.example.prayforthem.names.domain.models.Dignity
import com.example.prayforthem.names.domain.models.Name

data class PersonDignityName(
    val person: Person,
    val dignity: Dignity?,
    val name: Name
)