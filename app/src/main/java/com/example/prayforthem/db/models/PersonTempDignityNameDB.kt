package com.example.prayforthem.db.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.prayforthem.db.entities.DignityEntity
import com.example.prayforthem.db.entities.NameEntity
import com.example.prayforthem.db.entities.PersonTempEntity

data class PersonTempDignityNameDB(
    @Embedded val personTempEntity: PersonTempEntity,
    @Relation(
        parentColumn = "id_dignity",
        entityColumn = "dignity_id"
    )
    val dignity: DignityEntity?,

    @Relation(
        parentColumn = "id_name",
        entityColumn = "name_id"
    )
    val name: NameEntity
)