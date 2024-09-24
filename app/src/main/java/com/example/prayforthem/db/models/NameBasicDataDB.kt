package com.example.prayforthem.db.models

import androidx.room.ColumnInfo

data class NameBasicDataDB(
    @ColumnInfo(name = "name_id")
    val nameId: Int,
    @ColumnInfo(name = "name_display")
    val nameDisplay: String,
    @ColumnInfo(name = "name_gen")
    val nameGenitive: String
)