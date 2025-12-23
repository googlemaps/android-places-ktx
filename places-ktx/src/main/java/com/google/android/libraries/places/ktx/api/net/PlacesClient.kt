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
import androidx.annotation.RequiresPermission
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.model.LocationRestriction
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPhotoResponse
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.IsOpenResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.SearchByTextRequest
import com.google.android.libraries.places.api.net.SearchByTextResponse
import com.google.android.libraries.places.api.net.SearchNearbyResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await
import com.google.android.libraries.places.api.net.kotlin.awaitFetchPhoto as sdkAwaitFetchPhoto
import com.google.android.libraries.places.api.net.kotlin.awaitFetchPlace as sdkAwaitFetchPlace
import com.google.android.libraries.places.api.net.kotlin.awaitFindAutocompletePredictions as sdkAwaitFindAutocompletePredictions
import com.google.android.libraries.places.api.net.kotlin.awaitIsOpen as sdkAwaitIsOpen
import com.google.android.libraries.places.api.net.kotlin.awaitSearchByText as sdkAwaitSearchByText
import com.google.android.libraries.places.api.net.kotlin.awaitSearchNearby as sdkAwaitSearchNearby

/**
 * Wraps [PlacesClient.fetchPhoto] in a suspending function.
 *
 * Fetches a photo. If an error occurred, an [ApiException] will be thrown.
 */
@ExperimentalCoroutinesApi
@Deprecated(
    "Use fetchPhoto(FetchPhotoRequest) directly from the Places SDK.",
)
public suspend fun PlacesClient.awaitFetchPhoto(
    photoMetadata: PhotoMetadata,
    actions: FetchPhotoRequest.Builder.() -> Unit = {}
): FetchPhotoResponse {
    return this.sdkAwaitFetchPhoto(photoMetadata, actions)
}

/**
 * Wraps [PlacesClient.fetchPlace] in a suspending function.
 *
 * Fetches the details of a place. If an error occurred, an [ApiException] will be thrown.
 */
@ExperimentalCoroutinesApi
@Deprecated(
    "Use PlacesClient.awaitFetchPlace(String, List<Place.Field>) from the Places SDK.",
    ReplaceWith("this.awaitFetchPlace(placeId, placeFields)")
)
public suspend fun PlacesClient.awaitFetchPlace(
    placeId: String,
    placeFields: List<Place.Field>,
): FetchPlaceResponse {
    return this.sdkAwaitFetchPlace(placeId, placeFields)
}

/**
 * Wraps [PlacesClient.findAutocompletePredictions] in a suspending function.
 *
 * Fetches autocomplete predictions. If an error occurred, an [ApiException] will be thrown.
 */
@ExperimentalCoroutinesApi
@Deprecated(
    "Use PlacesClient.awaitFindAutocompletePredictions(FindAutocompletePredictionsRequest) from the Places SDK.",
)
public suspend fun PlacesClient.awaitFindAutocompletePredictions(
    actions: FindAutocompletePredictionsRequest.Builder.() -> Unit
): FindAutocompletePredictionsResponse {
    return this.sdkAwaitFindAutocompletePredictions(actions)
}

/**
 * Wraps [PlacesClient.findCurrentPlace] in a suspending function.
 *
 * Fetches the approximate current place of the user's device. Calling this method without granting
 * the appropriate permissions will result in a [SecurityException] being thrown. In addition, if
 * an error occurred while fetching the current place, an [ApiException] will be thrown.
 */

@ExperimentalCoroutinesApi
@RequiresPermission(anyOf = [permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION])
@Deprecated(
    "Use PlacesClient.awaitSearchNearby(SearchNearbyRequest) from the Places SDK.",
)
public suspend fun PlacesClient.awaitFindCurrentPlace(
    placeFields: List<Place.Field>
): FindCurrentPlaceResponse {
    val cancellationTokenSource = CancellationTokenSource()
    val request = FindCurrentPlaceRequest.builder(placeFields)
        .setCancellationToken(cancellationTokenSource.token)
        .build()
    return this.findCurrentPlace(request).await(cancellationTokenSource)
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
 * @return the response containing the nearby place results.
 */
@ExperimentalCoroutinesApi
@RequiresPermission(anyOf = [permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION])
@Deprecated(
    "Use PlacesClient.awaitSearchNearby(SearchNearbyRequest) from the Places SDK.",
)
public suspend fun PlacesClient.awaitSearchNearbyPlace(
    locationRestriction: LocationRestriction,
    placeFields: List<Place.Field>
): SearchNearbyResponse {
    return this.sdkAwaitSearchNearby(locationRestriction, placeFields)
}

/**
 * Wraps [PlacesClient.isOpen] in a suspending function with the given [Place] object.
 *
 * Returns whether or not a place is open. If an error occurred, an [ApiException] will be thrown.
 */
@ExperimentalCoroutinesApi
@Deprecated(
    "Use PlacesClient.awaitIsOpen(IsOpenRequest) from the Places SDK.",
)
public suspend fun PlacesClient.awaitIsOpen(
    place: Place, utcTimeMillis: Long? = null
): IsOpenResponse {
    return this.sdkAwaitIsOpen(place, utcTimeMillis)
}

/**
 * Wraps [PlacesClient.isOpen] in a suspending function with the given placeId.
 *
 * Returns whether or not a place is open. If an error occurred, an [ApiException] will be thrown.
 */
@ExperimentalCoroutinesApi
@Deprecated(
    "Use PlacesClient.awaitIsOpen(IsOpenRequest) from the Places SDK.",
)
public suspend fun PlacesClient.awaitIsOpen(
    placeId: String,
    utcTimeMillis: Long? = null,
): IsOpenResponse {
    return this.sdkAwaitIsOpen(placeId, utcTimeMillis)
}

@ExperimentalCoroutinesApi
/**
 * Wraps [PlacesClient.searchByText] in a suspending function.
 *
 * Fetches the place(s) of interest using a text query. If an error occurred, an [ApiException] will
 * be thrown.
 */
@Deprecated(
    "Use PlacesClient.awaitSearchByText(SearchByTextRequest) from the Places SDK.",
)
public suspend fun PlacesClient.awaitSearchByText(
    textQuery: String,
    placeFields: List<Place.Field>,
    actions: SearchByTextRequest.Builder.() -> Unit = {},
): SearchByTextResponse {
    return this.sdkAwaitSearchByText(textQuery, placeFields, actions)
}
