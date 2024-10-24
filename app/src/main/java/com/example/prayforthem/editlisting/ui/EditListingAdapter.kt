package com.example.prayforthem.editlisting.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.databinding.NamesBinListItemBinding
import com.example.prayforthem.listings.RecyclerViewClickInterface
import com.example.prayforthem.listings.domain.models.PersonDignityName
import com.example.prayforthem.utils.NameFormsConstructor

class EditListingAdapter(val clickInterface: RecyclerViewClickInterface<PersonDignityName>) :
    ListAdapter<PersonDignityName, EditListingAdapter.EditListingViewHolder>(
        EditListingDiffItemCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditListingViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = NamesBinListItemBinding.inflate(layoutInspector, parent, false)
        return EditListingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EditListingViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.basket.setOnClickListener {
            clickInterface.onDeleteElementClick(item)
        }
    }


    class EditListingViewHolder(val binding: NamesBinListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PersonDignityName) {
            binding.apply {
                name.text = NameFormsConstructor.createPersonDisplay(item)
            }
        }

    }

}