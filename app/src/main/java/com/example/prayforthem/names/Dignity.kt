package com.example.prayforthem.names

data class Dignity(
    val id: Int,
    val nameNominative: String, //именительный падеж
    val nameGenitive: String, //родительный падеж
    val nameDative: String, //дательный падеж
    val nameAccusative: String, //винительный падеж
    val nameInstrumental: String, //творительный падеж
    val namePrepositional: String, //предложный падеж
    val nameShort: String //сокращенная форма
)