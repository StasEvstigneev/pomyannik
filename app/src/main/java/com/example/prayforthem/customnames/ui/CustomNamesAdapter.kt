package com.example.prayforthem.customnames.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.databinding.ItemCustomNameBinding
import com.example.prayforthem.listings.domain.RecyclerViewDeleteItem
import com.example.prayforthem.listings.domain.RecyclerViewItemClick
import com.example.prayforthem.names.domain.models.Name

class CustomNamesAdapter(
    private val editClickInterface: RecyclerViewItemClick<Name>,
    private val deleteClickInterface: RecyclerViewDeleteItem<Name>
) : ListAdapter<Name, CustomNamesAdapter.CustomNamesViewHolder>(CustomNamesDiffItemCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomNamesViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = ItemCustomNameBinding.inflate(layoutInspector, parent, false)
        return CustomNamesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomNamesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.edit.setOnClickListener {
            editClickInterface.onItemClick(item)
        }
        holder.binding.basket.setOnClickListener {
            deleteClickInterface.onDeleteElementClick(item)
        }

    }

    class CustomNamesViewHolder(val binding: ItemCustomNameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Name) {
            binding.apply {
                name.text = item.nameDisplay
            }
        }

    }
}