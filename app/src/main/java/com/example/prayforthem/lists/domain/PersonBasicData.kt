package com.example.prayforthem.lists.domain

import com.example.prayforthem.names.domain.models.DignityBasicData
import com.example.prayforthem.names.domain.models.NameBasicData

data class PersonBasicData(
    val dignity: DignityBasicData? = null,
    val name: NameBasicData
)