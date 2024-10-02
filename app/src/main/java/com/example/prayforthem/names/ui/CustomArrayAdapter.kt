package com.example.prayforthem.names.ui

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import com.example.prayforthem.R

class CustomArrayAdapter<T>(
    context: Context,
    private val items: List<T>
) : ArrayAdapter<T>(context, R.layout.name_drop_down_item, items), Filterable {

    private var filteredItems: List<T> = items

    override fun getCount(): Int {
        return filteredItems.size
    }

    override fun getItem(position: Int): T? {
        return filteredItems[position]
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val query = constraint?.toString()?.lowercase() ?: ""

                filteredItems = if (query.isEmpty()) {
                    items
                } else {
                    items.filter { item -> item.toString().lowercase().startsWith(query) }
                }

                filterResults.values = filteredItems
                filterResults.count = filteredItems.size

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}