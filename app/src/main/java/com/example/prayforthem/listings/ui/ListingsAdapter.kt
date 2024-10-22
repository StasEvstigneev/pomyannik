package com.example.prayforthem.listings.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.R
import com.example.prayforthem.databinding.ListItemBinding
import com.example.prayforthem.listings.RecyclerViewClickInterface
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.domain.models.PersonDignityName

class ListingsAdapter(val clickInterface: RecyclerViewClickInterface<ListingWithPerson>) :
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
            clickInterface.onDeleteElementClick(item)
        }
        holder.itemView.setOnClickListener {
            clickInterface.onItemClick(item)
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
                    if (item.dignity != null) item.dignity.dignityShort + SPACE + item.name.nameDisplay
                    else item.name.nameDisplay
                )

            }
            return result.joinToString(separator = ", ")
        }

        companion object {
            private const val SPACE = " "
        }

    }


}