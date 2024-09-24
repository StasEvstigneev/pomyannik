package com.example.prayforthem.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dignity")
data class DignityEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "dignity_id")
    val dignityId: Int,
    @ColumnInfo(name = "dignity_display")
    val dignityDisplay: String,
    @ColumnInfo(name = "dignity_nom")
    val dignityNominative: String,
    @ColumnInfo(name = "dignity_gen")
    val dignityGenitive: String,
    @ColumnInfo(name = "dignity_dat")
    val dignityDative: String,
    @ColumnInfo(name = "dignity_acc")
    val dignityAccusative: String,
    @ColumnInfo(name = "dignity_inst")
    val dignityInstrumental: String,
    @ColumnInfo(name = "dignity_prep")
    val dignityPrepositional: String,
    @ColumnInfo(name = "dignity_short")
    val dignityShort: String,
    @ColumnInfo(name = "is_title")
    val isChurchTitle: Int //0-false, 1-true
)