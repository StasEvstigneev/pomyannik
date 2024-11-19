package com.example.prayforthem.editname.domain

data class TempName(
    val id: Int?,
    var nameDisplay: String?,
    var nameNominative: String?,
    var nameGenitive: String?,
    var nameDative: String?,
    var nameAccusative: String?,
    var nameInstrumental: String?,
    var namePrepositional: String?,
    val isCustom: Boolean
)