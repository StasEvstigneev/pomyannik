package com.example.prayforthem.prayers.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.databinding.PrayerItemBinding
import com.example.prayforthem.listings.RecyclerViewClickInterface
import com.example.prayforthem.prayers.domain.models.Prayer

class PrayersAdapter(
    var list: ArrayList<Prayer>,
    private val clickInterface: RecyclerViewClickInterface<Prayer>
) : RecyclerView.Adapter<PrayersAdapter.PrayersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrayersViewHolder {
        val layoutInspector = LayoutInflater.from(parent.context)
        val binding = PrayerItemBinding.inflate(layoutInspector, parent, false)
        return PrayersViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PrayersViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            clickInterface.onItemClick(item)
        }

    }

    class PrayersViewHolder(val binding: PrayerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(prayer: Prayer) {
            binding.title.text = prayer.title
        }
    }

}