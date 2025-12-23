// Copyright 2023 Google LLC
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

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.CircularBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.kotlin.awaitFetchResolvedPhotoUri
import com.google.android.libraries.places.api.net.kotlin.awaitSearchNearby
import com.google.android.libraries.places.ktx.api.net.awaitIsOpen
import com.google.android.libraries.places.ktx.api.net.awaitSearchByText
import com.google.places.android.ktx.demo.newapi.PlacesSearchViewModel.Companion.DEBOUNCE_DELAY_MS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for [PlacesSearchDemoActivity].
 *
 * This class demonstrates the usage of the New Places API via KTX extensions.
 * It handles text-based search (SearchByText) and location-based search (SearchNearby),
 * ensuring proper cancellation of in-flight requests when query text changes (debouncing).
 */
@HiltViewModel
class PlacesSearchViewModel @Inject constructor(
    private val placesClient: PlacesClient
) : ViewModel() {
    private val _events = MutableLiveData<PlacesSearchEvent>()
    val events: LiveData<PlacesSearchEvent> = _events

    private var searchJob: Job? = null

    /**
     * triggered when the user modifies the search query.
     *
     * Why Debounce?
     * Text watchers fire on every keystroke. To avoid hitting the Places API rate limits
     * and to reduce unnecessary network traffic, we wait for a short pause ([DEBOUNCE_DELAY_MS])
     * before executing the request.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun onSearchQueryChanged(query: String) {
        // Cancel any previous search job to ensure we only process the latest query.
        searchJob?.cancel()
        _events.value = PlacesSearchEventLoading

        val handler = CoroutineExceptionHandler { _, throwable ->
            _events.value = PlacesSearchEventError(throwable)
        }
        searchJob = viewModelScope.launch(handler) {
            delay(DEBOUNCE_DELAY_MS)
            
            // Use SearchByText for query-based results.
            // This is the modern replacement for the legacy FindAutocompletePredictions + FetchPlace flow.
            val response = placesClient.awaitSearchByText(query, placeFields) {
                setMaxResultCount(MAX_RESULTS)
            }
            _events.value = PlacesSearchEventFound(response.places)
        }
    }

    /**
     * Triggered when the user explicitly requests a nearby search (e.g., "Search Nearby" menu item).
     *
     * Why Hardcoded Location?
     * This demo focuses on API usage mechanics (request construction, response handling) rather than
     * device location permissions handling. We use a fixed location (Boulder, CO) to reliably
     * demonstrate [awaitSearchNearby].
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun onSearchNearby() {
        searchJob?.cancel()
        _events.value = PlacesSearchEventLoading
        val handler = CoroutineExceptionHandler { _, throwable ->
            _events.value = PlacesSearchEventError(throwable)
        }
        searchJob = viewModelScope.launch(handler) {
            // Search nearby Boulder, CO
            val boulderCenter = LatLng(40.014984, -105.270546)
            val circle = CircularBounds.newInstance(boulderCenter, SEARCH_RADIUS_METERS)
            
            val response = placesClient.awaitSearchNearby(circle, placeFields) {
                 setMaxResultCount(MAX_RESULTS)
                 setIncludedTypes(listOf(PlaceTypes.RESTAURANT))
            }
            _events.value = PlacesSearchEventFound(response.places)
        }
    }

    /**
     * Triggered when a user selects a place from the list.
     *
     * Why fetches details again?
     * While [SearchByText] and [SearchNearby] return [Place] objects, we might want to demonstrate
     * additional specific API calls like [awaitIsOpen] or [awaitFetchResolvedPhotoUri] which
     * allow for checking status or getting displayable assets.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun onPlaceClicked(place: Place) {
        val handler = CoroutineExceptionHandler { _, e ->
            Log.e(TAG, "Error in details check", e)
        }
        viewModelScope.launch(handler) {
            // 1. Check if the place is currently open.
            // This is a specific call that calculates open status based on current time.
            try {
                val isOpenResponse = placesClient.awaitIsOpen(place)
                Log.d(TAG, "Place ${place.displayName} is open: ${isOpenResponse.isOpen}")
            } catch (e: Exception) {
                 Log.e(TAG, "IsOpen failed", e)
            }

            // 2. Resolve a photo URI if metadata is available.
            // Photos are not returned as direct URIs in the Place object; they must be resolved
            // via a separate call to handle attribution and implementation details.
            val photoMetadata = place.photoMetadatas?.firstOrNull()
            if (photoMetadata != null) {
                try {
                   val photoUriResponse = placesClient.awaitFetchResolvedPhotoUri(photoMetadata)
                   Log.d(TAG, "Photo URI: ${photoUriResponse.uri}")
                } catch (e: Exception) {
                    Log.e(TAG, "FetchResolvedPhotoUri failed", e)
                }
            }
        }
    }

    companion object {
        private const val TAG = "PlacesSearchViewModel"
        private const val DEBOUNCE_DELAY_MS = 300L
        private const val SEARCH_RADIUS_METERS = 5000.0
        private const val MAX_RESULTS = 5

        // Fields to request from the API.
        // It is best practice to only request the fields you actually need to reduce latency and cost.
        val placeFields = listOf(
            Place.Field.ID,
            Place.Field.DISPLAY_NAME,
            Place.Field.FORMATTED_ADDRESS,
            Place.Field.LOCATION,
            Place.Field.PHOTO_METADATAS,
            Place.Field.OPENING_HOURS, // Required for awaitIsOpen
            Place.Field.UTC_OFFSET     // Required for awaitIsOpen
        )
    }
}