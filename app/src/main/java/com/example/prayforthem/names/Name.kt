package com.example.prayforthem.names

data class Name(
    var id: Int,
    var nameDisplay: String,
    var nameNominative: String, //именительный падеж
    var nameGenitive: String, //родительный падеж
    var nameDative: String, //дательный падеж
    var nameAccusative: String, //винительный падеж
    var nameInstrumental: String, //творительный падеж
    var namePrepositional: String //предложный падеж
)