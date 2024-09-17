package com.example.prayforthem.lists.domain

import com.example.prayforthem.names.Dignity
import com.example.prayforthem.names.Name

data class Person(
    var dignity: Dignity?,
    var name: Name
)