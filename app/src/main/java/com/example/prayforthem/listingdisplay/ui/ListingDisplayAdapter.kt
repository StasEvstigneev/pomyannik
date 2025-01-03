package com.example.prayforthem.listingdisplay.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.databinding.ListDispNameItemBinding
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.utils.NameFormsConstructor

class ListingDisplayAdapter(var list: List<PersonDignityName>) :
    RecyclerView.Adapter<ListingDisplayAdapter.ListingDisplayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingDisplayViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = ListDispNameItemBinding.inflate(layoutInspector, parent, false)
        return ListingDisplayViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListingDisplayViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }


    class ListingDisplayViewHolder(val binding: ListDispNameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(person: PersonDignityName) {
            binding.root.text = NameFormsConstructor.createPersonShortGenitive(person)
        }

    }
}