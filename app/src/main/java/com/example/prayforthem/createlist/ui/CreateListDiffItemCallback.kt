package com.example.prayforthem.createlist.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.prayforthem.lists.domain.PersonBasicData

class CreateListDiffItemCallback: DiffUtil.ItemCallback<PersonBasicData>() {
    override fun areItemsTheSame(oldItem: PersonBasicData, newItem: PersonBasicData): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: PersonBasicData, newItem: PersonBasicData): Boolean {
        return oldItem == newItem
    }
}