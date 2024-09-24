package com.example.prayforthem.db.models

import androidx.room.ColumnInfo

data class DignityBasicDataDB(
    @ColumnInfo(name = "dignity_id")
    val dignityId: Int,
    @ColumnInfo(name = "dignity_display")
    val dignityDisplay: String,
    @ColumnInfo(name = "dignity_short")
    val dignityShort: String
)