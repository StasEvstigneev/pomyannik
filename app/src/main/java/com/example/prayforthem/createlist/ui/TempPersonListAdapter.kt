package com.example.prayforthem.createlist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prayforthem.databinding.NamesBinListItemBinding
import com.example.prayforthem.lists.domain.PersonBasicData

class TempPersonListAdapter(
    var list: ArrayList<PersonBasicData>,
    private val recyclerViewClickInterface: TempPersonClickInterface
) : RecyclerView.Adapter<TempPersonListAdapter.PersonBDViewHolder>() {

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
            val dignity: String? =
                if (person.dignity != null) person.dignity.dignityDisplay else null
            val name = person.name.nameDisplay
            binding.name.text = if (dignity != null) dignity + SPACE + name else name
        }

    }

    companion object {
        private const val SPACE = " "
    }

}