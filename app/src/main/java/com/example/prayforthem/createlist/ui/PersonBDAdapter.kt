package com.example.prayforthem.createlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.databinding.NamesBinListItemBinding
import com.example.prayforthem.lists.domain.PersonBasicData
import com.example.prayforthem.utils.RecyclerViewClickInterface

class PersonBDAdapter(
    private val recyclerViewClickInterface: RecyclerViewClickInterface<PersonBasicData>
) : ListAdapter<PersonBasicData, PersonBDAdapter.PersonBDViewHolder>(CreateListDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonBDViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = NamesBinListItemBinding.inflate(layoutInspector, parent, false)
        return PersonBDViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonBDViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.basket.setOnClickListener {
            recyclerViewClickInterface.onTrashBinClick(position)
        }
    }


    class PersonBDViewHolder(val binding: NamesBinListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(person: PersonBasicData) {
            val dignity: String = if (person.dignity != null) person.dignity.dignityDisplay else ""
            val name = person.name.nameDisplay
            binding.name.text = dignity + " " + name
        }

    }


}