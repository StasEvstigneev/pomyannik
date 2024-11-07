package com.example.prayforthem.prayeraddnames.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.databinding.NamesBinListItemBinding
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.utils.NameFormsConstructor

class PrayerAddNamesAdapter(
    var list: ArrayList<PersonDignityName>,
    private val onDeleteClickInterface: TempPersonRemoveClickInterface<PersonDignityName>
) : RecyclerView.Adapter<PrayerAddNamesAdapter.PrayerAddNamesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayerAddNamesViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = NamesBinListItemBinding.inflate(layoutInspector, parent, false)
        return PrayerAddNamesViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PrayerAddNamesViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.binding.basket.setOnClickListener {
            onDeleteClickInterface.removeTempPerson(item, position)
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