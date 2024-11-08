package com.example.prayforthem.chooselisting.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.R
import com.example.prayforthem.databinding.ListItemCheckboxBinding
import com.example.prayforthem.listings.domain.models.ListingWithPerson
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.utils.NameFormsConstructor

class ListingsCheckboxAdapter(
    var list: ArrayList<ListingWithPerson>,
    private val checkboxClickInterface: RecyclerViewCheckboxInterface<ListingWithPerson>
) : RecyclerView.Adapter<ListingsCheckboxAdapter.ListingsCheckboxViewHolder>() {

    private var selectedLists: ArrayList<Int> = arrayListOf()

    fun updateSelectedLists(ids: ArrayList<Int>) {
        selectedLists = ids
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListingsCheckboxViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = ListItemCheckboxBinding.inflate(layoutInspector, parent, false)
        return ListingsCheckboxViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ListingsCheckboxViewHolder,
        position: Int
    ) {
        val item = list[position]
        holder.bind(item)
        holder.binding.checkbox.isChecked = selectedLists.contains(item.listing.listingId)
        holder.binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            checkboxClickInterface.onCheckBoxClick(item, isChecked)
        }
    }

    override fun getItemCount(): Int = list.size

    class ListingsCheckboxViewHolder(val binding: ListItemCheckboxBinding) :
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