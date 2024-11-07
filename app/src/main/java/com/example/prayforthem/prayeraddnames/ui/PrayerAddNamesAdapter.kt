package com.example.prayforthem.prayeraddnames.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.databinding.NamesBinListItemBinding
import com.example.prayforthem.listings.domain.RecyclerViewDeleteItem
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.utils.NameFormsConstructor

class PrayerAddNamesAdapter(
    private val onDeleteClickInterface: RecyclerViewDeleteItem<PersonDignityName>
) : ListAdapter<PersonDignityName, PrayerAddNamesAdapter.PrayerAddNamesViewHolder>(
    PrayerAddNamesDiffItemCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayerAddNamesViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = NamesBinListItemBinding.inflate(layoutInspector, parent, false)
        return PrayerAddNamesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrayerAddNamesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.basket.setOnClickListener {
            onDeleteClickInterface.onDeleteElementClick(item)
        }
    }


    class PrayerAddNamesViewHolder(val binding: NamesBinListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PersonDignityName) {
            binding.apply {
                name.text = NameFormsConstructor.createPersonDisplay(item)
            }

        }
    }

}