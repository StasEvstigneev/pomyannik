package com.example.prayforthem.listings.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.R
import com.example.prayforthem.databinding.ListItemBinding
import com.example.prayforthem.listings.domain.RecyclerViewDeleteItem
import com.example.prayforthem.listings.domain.RecyclerViewItemClick
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.utils.NameFormsConstructor

class ListingsAdapter(
    private val itemClickInterface: RecyclerViewItemClick<ListingWithPerson>,
    private val deleteItemInterface: RecyclerViewDeleteItem<ListingWithPerson>
) :
    ListAdapter<ListingWithPerson, ListingsAdapter.ListingsViewHolder>(ListingsDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingsViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(layoutInspector, parent, false)
        return ListingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListingsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.basket.setOnClickListener {
            deleteItemInterface.onDeleteElementClick(item)
        }
        holder.itemView.setOnClickListener {
            itemClickInterface.onItemClick(item)
        }
    }

    class ListingsViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListingWithPerson) {
            binding.apply {
                listingName.text = item.listing.title
                cross.setImageResource(
                    if (item.listing.forHealth) R.drawable.cross_orthodox_red_alt
                    else R.drawable.cross_orthodox_black_alt
                )
                names.text = preparePersonDisplay(item.personListing)
            }
        }

        private fun preparePersonDisplay(personListing: List<PersonDignityName>): String {
            val result = ArrayList<String>()
            personListing.forEach { item ->
                result.add(
                    NameFormsConstructor.createPersonDisplay(item)
                )

            }
            return result.joinToString(separator = ", ")
        }

    }

}