// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.places.android.ktx.demo.newapi

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.libraries.places.api.model.Place

/**
 * A [ListAdapter] for displaying [Place] objects.
 *
 * This adapter demonstrates how to bind Place details to a UI list item.
 */
class PlaceResultAdapter : ListAdapter<Place, PlaceResultAdapter.PlaceResultViewHolder>(PlaceDiffCallback) {
    var onPlaceClickListener: ((Place) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaceResultViewHolder(
            inflater.inflate(R.layout.place_prediction_item, parent, false))
    }

    override fun onBindViewHolder(holder: PlaceResultViewHolder, position: Int) {
        val place = getItem(position)
        holder.setPlace(place)
        holder.itemView.setOnClickListener {
            onPlaceClickListener?.invoke(place)
        }
    }



    class PlaceResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.text_view_title)
        private val address: TextView = itemView.findViewById(R.id.text_view_address)

        fun setPlace(place: Place) {
            title.text = place.displayName ?: place.id ?: ""
            address.text = place.formattedAddress ?: ""
            
            // Minimal fallback: if address is missing, show location coordinates.
            // In a real app, you might want to show a specific "Address not available" string or hide the view.
            if (address.text.isEmpty()) {
                address.text = place.location?.toString() ?: ""
            }
        }
    }

    companion object PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {
        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem.displayName == newItem.displayName &&
                   oldItem.formattedAddress == newItem.formattedAddress &&
                   oldItem.location == newItem.location
        }
    }
}