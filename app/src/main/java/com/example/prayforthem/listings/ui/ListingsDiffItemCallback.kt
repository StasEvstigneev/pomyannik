package com.example.prayforthem.listings.ui

import androidx.recyclerview.widget.DiffUtil
import com.example.prayforthem.listings.domain.models.ListingWithPerson

class ListingsDiffItemCallback: DiffUtil.ItemCallback<ListingWithPerson>() {
    override fun areItemsTheSame(oldItem: ListingWithPerson, newItem: ListingWithPerson): Boolean {
        return oldItem.listing.listingId == newItem.listing.listingId
    }

    override fun areContentsTheSame(
        oldItem: ListingWithPerson,
        newItem: ListingWithPerson
    ): Boolean {
        return oldItem == newItem
    }
}