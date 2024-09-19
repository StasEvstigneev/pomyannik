package com.example.prayforthem.names

data class Name(
    val id: Int,
    val nameDisplay: String,
    val nameNominative: String, //именительный падеж
    val nameGenitive: String, //родительный падеж
    val nameDative: String, //дательный падеж
    val nameAccusative: String, //винительный падеж
    val nameInstrumental: String, //творительный падеж
    val namePrepositional: String, //предложный падеж
    val isCustom: Boolean
)