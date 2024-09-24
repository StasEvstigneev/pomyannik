package com.example.prayforthem.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prayforthem.db.dao.DignityDao
import com.example.prayforthem.db.dao.NameDao
import com.example.prayforthem.db.entities.DignityEntity
import com.example.prayforthem.db.entities.NameEntity

@Database(
    version = 1,
    entities = [NameEntity::class, DignityEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun nameDao(): NameDao
    abstract fun dignityDao(): DignityDao
}