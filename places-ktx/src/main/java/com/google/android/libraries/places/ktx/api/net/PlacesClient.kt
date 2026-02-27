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

package com.google.android.libraries.places.ktx.api.net

import android.Manifest.permission
import android.annotation.SuppressLint
import androidx.annotation.RequiresPermission
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.LocationRestriction
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FetchResolvedPhotoUriRequest
import com.google.android.libraries.places.api.net.FetchResolvedPhotoUriResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.IsOpenResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchByTextRequest
import com.google.android.libraries.places.api.net.SearchByTextResponse
import com.google.android.libraries.places.api.net.SearchNearbyRequest
import com.google.android.libraries.places.api.net.SearchNearbyResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.android.libraries.places.api.net.kotlin.awaitFetchPlace as sdkAwaitFetchPlace
import com.google.android.libraries.places.api.net.kotlin.awaitFetchResolvedPhotoUri as sdkAwaitFetchResolvedPhotoUri
import com.google.android.libraries.places.api.net.kotlin.awaitFindAutocompletePredictions as sdkAwaitFindAutocompletePredictions
import com.google.android.libraries.places.api.net.kotlin.awaitIsOpen as sdkAwaitIsOpen
import com.google.android.libraries.places.api.net.kotlin.awaitSearchByText as sdkAwaitSearchByText
import com.google.android.libraries.places.api.net.kotlin.awaitSearchNearby as sdkAwaitSearchNearby

/**
 * Wraps [PlacesClient.fetchPlace] in a suspending function.
 *
 * Fetches the details of a place. If an error occurred, an [ApiException] will be thrown.
 */
@Deprecated(
    "Use the version in the Places SDK instead.",
    ReplaceWith("this.awaitFetchPlace(placeId, placeFields, actions)")
)
@ExperimentalCoroutinesApi
public suspend fun PlacesClient.awaitFetchPlace(
    placeId: String,
    placeFields: List<Place.Field>,
): FetchPlaceResponse {
    return this.sdkAwaitFetchPlace(placeId, placeFields)
}

/**
 * Wraps [PlacesClient.fetchResolvedPhotoUri] in a suspending function.
 *
 * Fetches a resolved photo URI. If an error occurred, an [ApiException] will be thrown.
 */
@Deprecated(
    "Use the version in the Places SDK instead.",
    ReplaceWith("this.awaitFetchResolvedPhotoUri(photoMetadata, actions)")
)
@ExperimentalCoroutinesApi
public suspend fun PlacesClient.awaitFetchResolvedPhotoUri(
    photoMetadata: PhotoMetadata,
    actions: FetchResolvedPhotoUriRequest.Builder.() -> Unit = {}
): FetchResolvedPhotoUriResponse {
    return this.sdkAwaitFetchResolvedPhotoUri(photoMetadata, actions)
}

/**
 * Wraps [PlacesClient.findAutocompletePredictions] in a suspending function.
 *
 * Fetches autocomplete predictions. If an error occurred, an [ApiException] will be thrown.
 */
@Deprecated(
    "Use the version in the Places SDK instead.",
    ReplaceWith("this.awaitFindAutocompletePredictions(actions)")
)
@ExperimentalCoroutinesApi
public suspend fun PlacesClient.awaitFindAutocompletePredictions(
    actions: FindAutocompletePredictionsRequest.Builder.() -> Unit
): FindAutocompletePredictionsResponse {
    return this.sdkAwaitFindAutocompletePredictions(actions)
}

/**
 * Wraps [PlacesClient.searchNearby] in a suspending function.
 *
 * Fetches the approximate nearby places based on the provided [locationRestriction]. Calling this
 * method without granting the appropriate permissions will result in a [SecurityException] being
 * thrown. In addition, if an error occurred while fetching the places, an [ApiException] will be
 * thrown.
 *
 * @param locationRestriction limits the scope of the search to a specific area.
 * @param placeFields the fields of the places to be returned
 * @param actions the actions to apply to the [SearchNearbyRequest.Builder]
 * @return the response containing the nearby place results.
 */
@Deprecated(
    "Use the version in the Places SDK instead.",
    ReplaceWith("this.awaitSearchNearby(locationRestriction, placeFields, actions)")
)
@ExperimentalCoroutinesApi
@RequiresPermission(anyOf = [permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION])
@SuppressLint("MissingPermission")
public suspend fun PlacesClient.awaitSearchNearby(
    locationRestriction: LocationRestriction,
    placeFields: List<Place.Field>,
    actions: SearchNearbyRequest.Builder.() -> Unit = {}
): SearchNearbyResponse {
    return this.sdkAwaitSearchNearby(locationRestriction, placeFields, actions)
}

/**
 * Wraps [PlacesClient.isOpen] in a suspending function with the given [Place] object.
 *
 * Returns whether a place is open. If an error occurred, an [ApiException] will be thrown.
 */
@Deprecated(
    "Use the version in the Places SDK instead.",
    ReplaceWith("this.awaitIsOpen(place, utcTimeMillis)")
)
@ExperimentalCoroutinesApi
public suspend fun PlacesClient.awaitIsOpen(
    place: Place, utcTimeMillis: Long? = null
): IsOpenResponse {
    return this.sdkAwaitIsOpen(place, utcTimeMillis ?: 0L)
}

/**
 * Wraps [PlacesClient.isOpen] in a suspending function with the given placeId.
 *
 * Returns whether or not a place is open. If an error occurred, an [ApiException] will be thrown.
 */
@Deprecated(
    "Use the version in the Places SDK instead.",
    ReplaceWith("this.awaitIsOpen(placeId, utcTimeMillis)")
)
@ExperimentalCoroutinesApi
public suspend fun PlacesClient.awaitIsOpen(
    placeId: String,
    utcTimeMillis: Long? = null,
): IsOpenResponse {
    return this.sdkAwaitIsOpen(placeId, utcTimeMillis ?: 0L)
}

@Deprecated(
    "Use the version in the Places SDK instead.",
    ReplaceWith("this.awaitSearchByText(textQuery, placeFields, actions)")
)
@ExperimentalCoroutinesApi
/**
 * Wraps [PlacesClient.searchByText] in a suspending function.
 *
 * Fetches the place(s) of interest using a text query. If an error occurred, an [ApiException] will
 * be thrown.
 */
public suspend fun PlacesClient.awaitSearchByText(
    textQuery: String,
    placeFields: List<Place.Field>,
    actions: SearchByTextRequest.Builder.() -> Unit = {},
): SearchByTextResponse {
    return this.sdkAwaitSearchByText(textQuery, placeFields, actions)
}

