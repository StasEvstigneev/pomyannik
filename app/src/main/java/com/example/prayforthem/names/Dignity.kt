package com.example.prayforthem.names

data class Dignity(
    val id: Int,
    val dignityDisplay: String,
    val dignityNominative: String, //именительный падеж
    val dignityGenitive: String, //родительный падеж
    val dignityDative: String, //дательный падеж
    val dignityAccusative: String, //винительный падеж
    val dignityInstrumental: String, //творительный падеж
    val dignityPrepositional: String, //предложный падеж
    val dignityShort: String, //сокращенная форма
    val isChurchTitle: Boolean
)