package com.example.prayforthem.editlisting.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.prayforthem.listings.domain.models.PersonDignityName

class EditListingDiffItemCallback : DiffUtil.ItemCallback<PersonDignityName>() {
    override fun areItemsTheSame(oldItem: PersonDignityName, newItem: PersonDignityName): Boolean {
        return oldItem.person.id == newItem.person.id
    }

    override fun areContentsTheSame(
        oldItem: PersonDignityName,
        newItem: PersonDignityName
    ): Boolean {
        return oldItem == newItem
    }
}