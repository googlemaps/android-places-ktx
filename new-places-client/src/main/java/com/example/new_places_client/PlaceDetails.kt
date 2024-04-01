// Copyright 2024 Google LLC
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

package com.example.new_places_client

import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place

// Simple data class to hold the place data needed from the search results
data class PlaceDetails(
    val placeId: String,
    val name: String,
    val location: LatLng,
    val address: String? = null,
)

fun Place.toPlaceDetails(): PlaceDetails? {
    val name = this.name
    val placeId = this.id
    val address = this.address
    val latLng = this.latLng
    return if (placeId != null && name != null && latLng != null) {
        PlaceDetails(
            placeId = placeId,
            name = name,
            location = latLng,
            address = address
        )
    } else {
        null
    }
}