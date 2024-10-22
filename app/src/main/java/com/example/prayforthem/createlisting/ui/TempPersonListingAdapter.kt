package com.example.prayforthem.createlisting.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.databinding.NamesBinListItemBinding
import com.example.prayforthem.listings.domain.models.PersonBasicData
import com.example.prayforthem.utils.NameFormsConstructor

class TempPersonListingAdapter(
    var list: ArrayList<PersonBasicData>,
    private val recyclerViewClickInterface: TempPersonClickInterface
) : RecyclerView.Adapter<TempPersonListingAdapter.PersonBDViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonBDViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = NamesBinListItemBinding.inflate(layoutInspector, parent, false)
        return PersonBDViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PersonBDViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.binding.basket.setOnClickListener {
            recyclerViewClickInterface.onTrashBinClick(position)
        }
    }

    class PersonBDViewHolder(val binding: NamesBinListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(person: PersonBasicData) {
            binding.name.text = NameFormsConstructor.createPersonDisplay(person)
        }
    }

}