package com.example.prayforthem.names.domain.models

sealed class NamesScreenState() {
    data object Loading : NamesScreenState()
    data class Default(
        val dignity: ArrayList<DignityBasicData>,
        val names: ArrayList<NameBasicData>
    ) : NamesScreenState()
}