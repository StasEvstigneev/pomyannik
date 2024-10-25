package com.example.prayforthem.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prayforthem.db.dao.DignityDao
import com.example.prayforthem.db.dao.ListingDao
import com.example.prayforthem.db.dao.NameDao
import com.example.prayforthem.db.dao.PersonDao
import com.example.prayforthem.db.dao.PrayerCategoryDao
import com.example.prayforthem.db.dao.PrayerDao
import com.example.prayforthem.db.entities.DignityEntity
import com.example.prayforthem.db.entities.ListingEntity
import com.example.prayforthem.db.entities.NameEntity
import com.example.prayforthem.db.entities.PersonEntity
import com.example.prayforthem.db.entities.PrayerCategoryEntity
import com.example.prayforthem.db.entities.PrayerCategoryPrayerCrossRef
import com.example.prayforthem.db.entities.PrayerEntity

@Database(
    version = 1,
    entities = [
        NameEntity::class,
        DignityEntity::class,
        PersonEntity::class,
        ListingEntity::class,
        PrayerEntity::class,
        PrayerCategoryEntity::class,
        PrayerCategoryPrayerCrossRef::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun nameDao(): NameDao
    abstract fun dignityDao(): DignityDao
    abstract fun personDao(): PersonDao
    abstract fun listingDao(): ListingDao
    abstract fun prayerDao(): PrayerDao
    abstract fun prayerCategoryDao(): PrayerCategoryDao
}