// Copyright 2026 Google LLC
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

package com.google.android.libraries.places.ktx.api.net

import com.google.android.libraries.places.api.model.LocationRestriction
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.SearchNearbyRequest
import com.google.android.libraries.places.api.net.kotlin.searchNearbyRequest as sdkSearchNearbyRequest

/**
 * Builds a new [SearchNearbyRequest] to find the current place.
 *
 * @param locationRestriction limits the scope of the search to a specific area.
 * @param placeFields the fields of the places to be returned
 * @param actions the actions to apply to the [SearchNearbyRequest.Builder]
 *
 * @return the constructed [SearchNearbyRequest]
 */
@Deprecated(
    "Use the version in the Places SDK instead.",
    ReplaceWith("searchNearbyRequest(locationRestriction, placeFields, actions)", "com.google.android.libraries.places.api.net.kotlin.searchNearbyRequest")
)
public fun searchNearbyRequest(
    locationRestriction: LocationRestriction,
    placeFields: List<Place.Field>,
    actions: (SearchNearbyRequest.Builder.() -> Unit)? = null
): SearchNearbyRequest {
    return sdkSearchNearbyRequest(locationRestriction, placeFields, actions)
}
