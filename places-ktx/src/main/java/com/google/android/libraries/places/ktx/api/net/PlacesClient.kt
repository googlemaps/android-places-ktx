package com.google.android.libraries.places.ktx.api.net

import androidx.annotation.RequiresPermission
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPhotoResponse
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.tasks.await

/**
 * Wraps [PlacesClient.fetchPhoto] in a suspending function.
 *
 * Fetches a photo. If an error occurred, an [ApiException] will be thrown.
 */
suspend fun PlacesClient.awaitFetchPhoto(request: FetchPhotoRequest): FetchPhotoResponse =
    this.fetchPhoto(request).await()

/**
 * Wraps [PlacesClient.fetchPlace] in a suspending function.
 *
 * Fetches the details of a place. If an error occurred, an [ApiException] will be thrown.
 */
suspend fun PlacesClient.awaitFetchPlace(request: FetchPlaceRequest): FetchPlaceResponse =
    this.fetchPlace(request).await()

/**
 * Wraps [PlacesClient.findAutocompletePredictions] in a suspending function.
 *
 * Fetches autocomplete predictions. If an error occurred, an [ApiException] will be thrown.
 */
suspend fun PlacesClient.awaitFindAutocompletePredictions(
    request: FindAutocompletePredictionsRequest
): FindAutocompletePredictionsResponse =
    this.findAutocompletePredictions(request).await()

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
suspend fun PlacesClient.awaitFindCurrentPlace(
    request: FindCurrentPlaceRequest
): FindCurrentPlaceResponse =
    this.findCurrentPlace(request).await()
