package com.example.prayforthem.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "names")
data class NameEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "name_id")
    val nameId: Int,
    @ColumnInfo(name = "name_display")
    val nameDisplay: String,
    @ColumnInfo(name = "name_nom")
    val nameNominative: String,
    @ColumnInfo(name = "name_gen")
    val nameGenitive: String,
    @ColumnInfo(name = "name_dat")
    val nameDative: String,
    @ColumnInfo(name = "name_acc")
    val nameAccusative: String,
    @ColumnInfo(name = "name_inst")
    val nameInstrumental: String,
    @ColumnInfo(name = "name_prep")
    val namePrepositional: String,
    @ColumnInfo(name = "custom")
    val isCustom: Boolean
)