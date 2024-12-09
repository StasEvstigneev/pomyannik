package com.example.prayforthem.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.prayforthem.db.entities.ListingEntity
import com.example.prayforthem.db.entities.PersonEntity
import com.example.prayforthem.db.models.ListingWithPersonDB
import com.example.prayforthem.db.models.ListingWithTempPersonDB

@Dao
interface ListingDao {

    @Insert(entity = ListingEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun addListing(listing: ListingEntity): Long

    @Transaction
    @Query("SELECT * FROM listing WHERE listing_id = :id")
    suspend fun getListingById(id: Int): ListingWithPersonDB

    @Transaction
    @Query("SELECT * FROM listing WHERE for_health = :isForHealth AND listing_id > 5") // добавил id>5, чтобы исключить резервные списки
    suspend fun getListings(isForHealth: Int): List<ListingWithPersonDB>

    @Transaction
    @Delete
    suspend fun deleteListing(listing: ListingEntity)

    @Update
    suspend fun updateListing(listing: ListingEntity)

    @Transaction
    @Query("SELECT * FROM listing WHERE listing_id = :id")
    suspend fun getReservedListingById(id: Int): ListingWithTempPersonDB

    @Insert(entity = PersonEntity::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPerson(person: PersonEntity)

    @Delete(entity = PersonEntity::class)
    suspend fun deletePerson(person: PersonEntity)

    @Transaction
    suspend fun createListing(listing: ListingEntity, personData: List<Pair<Int?, Int>>) {
        val listingId = addListing(listing).toInt()
        personData.forEach { pair ->
            addPerson(
                PersonEntity(
                    personId = null,
                    idDignity = pair.first,
                    idName = pair.second,
                    parentListingId = listingId
                )
            )
        }
    }

    @Transaction
    suspend fun updateListingData(
        personDel: List<PersonEntity>,
        listing: ListingEntity,
        personAdd: List<PersonEntity>
    ) {
        personDel.forEach { person -> deletePerson(person) }
        updateListing(listing)
        personAdd.forEach { newPerson -> addPerson(newPerson) }
    }

    @Transaction
    suspend fun deleteListingData(
        personDel: List<PersonEntity>,
        listing: ListingEntity
    ) {
        personDel.forEach { person -> deletePerson(person) }
        deleteListing(listing)
    }

}