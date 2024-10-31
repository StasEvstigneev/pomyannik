package com.example.prayforthem.prayerscategories.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.databinding.PrayerItemBinding
import com.example.prayforthem.listings.RecyclerViewClickInterface
import com.example.prayforthem.prayerscategories.domain.models.PrayerCategory

class PrayerCategoriesAdapter(
    var list: ArrayList<PrayerCategory>,
    private val clickInterface: RecyclerViewClickInterface<PrayerCategory>
) : RecyclerView.Adapter<PrayerCategoriesAdapter.PrayerCategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayerCategoriesViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = PrayerItemBinding.inflate(layoutInspector, parent, false)
        return PrayerCategoriesViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PrayerCategoriesViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickInterface.onItemClick(item)
        }
    }

    class PrayerCategoriesViewHolder(val binding: PrayerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: PrayerCategory) {
            binding.title.text = category.categoryTitle
        }

    }
}