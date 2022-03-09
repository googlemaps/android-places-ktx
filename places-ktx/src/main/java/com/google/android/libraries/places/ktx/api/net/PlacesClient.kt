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

import androidx.annotation.RequiresPermission
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPhotoResponse
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.ktx.api.model.photoMetadata
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await

/**
 * Wraps [PlacesClient.fetchPhoto] in a suspending function.
 *
 * Fetches a photo. If an error occurred, an [ApiException] will be thrown.
 */
@ExperimentalCoroutinesApi
public suspend fun PlacesClient.awaitFetchPhoto(
    photoMetadata: PhotoMetadata,
    actions: FetchPhotoRequest.Builder.() -> Unit = {}
): FetchPhotoResponse {
    val cancellationTokenSource = CancellationTokenSource()
    val request = FetchPhotoRequest.builder(photoMetadata)
        .setCancellationToken(cancellationTokenSource.token)
        .apply(actions)
        .build()
    return this.fetchPhoto(request)
        .await(cancellationTokenSource)
}

/**
 * Wraps [PlacesClient.fetchPlace] in a suspending function.
 *
 * Fetches the details of a place. If an error occurred, an [ApiException] will be thrown.
 */
@ExperimentalCoroutinesApi
public suspend fun PlacesClient.awaitFetchPlace(
    placeId: String,
    placeFields: List<Place.Field>,
): FetchPlaceResponse {
    val cancellationTokenSource = CancellationTokenSource()
    val request = FetchPlaceRequest.builder(placeId, placeFields)
        .setCancellationToken(cancellationTokenSource.token)
        .build()
    return this.fetchPlace(request).await(cancellationTokenSource)
}

/**
 * Wraps [PlacesClient.findAutocompletePredictions] in a suspending function.
 *
 * Fetches autocomplete predictions. If an error occurred, an [ApiException] will be thrown.
 */
@ExperimentalCoroutinesApi
public suspend fun PlacesClient.awaitFindAutocompletePredictions(
    actions: FindAutocompletePredictionsRequest.Builder.() -> Unit
): FindAutocompletePredictionsResponse {
    val cancellationTokenSource = CancellationTokenSource()
    val request = FindAutocompletePredictionsRequest.builder()
        .setCancellationToken(cancellationTokenSource.token)
        .apply(actions)
        .build()
    return this.findAutocompletePredictions(request).await(cancellationTokenSource)
}

/**
 * Wraps [PlacesClient.findCurrentPlace] in a suspending function.
 *
 * Fetches the approximate current place of the user's device. Calling this method without granting
 * the appropriate permissions will result in a [SecurityException] being thrown. In addition, if
 * an error occurred while fetching the current place, an [ApiException] will be thrown.
 */
@RequiresPermission(
    allOf = ["android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_WIFI_STATE"]
)
@ExperimentalCoroutinesApi
public suspend fun PlacesClient.awaitFindCurrentPlace(
    placeFields: List<Place.Field>
): FindCurrentPlaceResponse {
    val cancellationTokenSource = CancellationTokenSource()
    val request = FindCurrentPlaceRequest.builder(placeFields)
        .setCancellationToken(cancellationTokenSource.token)
        .build()
    return this.findCurrentPlace(request).await(cancellationTokenSource)
}
