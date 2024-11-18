package com.example.prayforthem.customnames.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.prayforthem.names.domain.models.Name

class CustomNamesDiffItemCallback : DiffUtil.ItemCallback<Name>() {
    override fun areItemsTheSame(oldItem: Name, newItem: Name): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Name, newItem: Name): Boolean {
        return oldItem == newItem
    }
}