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

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.libraries.places.api.model.Place
import java.util.*

/**
 * A [RecyclerView.Adapter] for a [com.google.android.libraries.places.api.model.Place].
 */
class PlaceResultAdapter : RecyclerView.Adapter<PlaceResultAdapter.PlaceResultViewHolder>() {
    private val places: MutableList<Place> = ArrayList()
    var onPlaceClickListener: ((Place) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PlaceResultViewHolder(
            inflater.inflate(R.layout.place_prediction_item, parent, false))
    }

    override fun onBindViewHolder(holder: PlaceResultViewHolder, position: Int) {
        val place = places[position]
        holder.setPlace(place)
        holder.itemView.setOnClickListener {
            onPlaceClickListener?.invoke(place)
        }
    }


    override fun getItemCount(): Int =
        places.size

    fun setPlaces(places: List<Place>) {
        this.places.clear()
        this.places.addAll(places)
        notifyDataSetChanged()
    }

    class PlaceResultViewHolder(itemView: View) : ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.text_view_title)
        private val address: TextView = itemView.findViewById(R.id.text_view_address)

        fun setPlace(place: Place) {
            title.text = place.displayName ?: place.id ?: ""
            address.text = place.formattedAddress ?: ""
            // Fallback to lat/lng or other fields if address is missing?
            if (address.text.isEmpty()) {
                address.text = place.location?.toString() ?: ""
            }
        }
    }
}