package com.example.prayforthem.names.domain.models

data class DignityBasicData(
    val dignityId: Int,
    val dignityDisplay: String, //для вывода в recyclerview
    val dignityShort: String //для вывода в списках
) {
    override fun toString(): String {
        return dignityDisplay
    }
}