package com.example.prayforthem.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [NameEntity::class, DignityEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
}