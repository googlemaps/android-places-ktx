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

package com.google.places.android.ktx.demo

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.api.net.kotlin.awaitFetchPlace
import com.google.android.libraries.places.api.net.kotlin.awaitFetchResolvedPhotoUri
import com.google.android.libraries.places.api.net.kotlin.awaitFindAutocompletePredictions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

/**
 * State definitions for the photo fetching demo.
 */
sealed interface PhotoDemoEvent
object PhotoDemoEventIdle : PhotoDemoEvent
object PhotoDemoEventLoading : PhotoDemoEvent
data class PhotoDemoEventResults(val predictions: List<AutocompletePrediction>) : PhotoDemoEvent
data class PhotoDemoEventError(val exception: Exception) : PhotoDemoEvent

data class PhotoState(
    val uri: Uri? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class PlacesPhotoViewModel @Inject constructor(
    private val placesClient: PlacesClient
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private var sessionToken: AutocompleteSessionToken? = null

    private val _photoState = MutableStateFlow(PhotoState())
    val photoState: StateFlow<PhotoState> = _photoState

    /**
     * Exposes search results based on the query.
     */
    val searchResults: StateFlow<PhotoDemoEvent> = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .mapLatest { query ->
            if (query.isBlank()) return@mapLatest PhotoDemoEventIdle

            try {
                if (sessionToken == null) {
                    sessionToken = AutocompleteSessionToken.newInstance()
                }

                val response = placesClient.awaitFindAutocompletePredictions {
                    sessionToken = this@PlacesPhotoViewModel.sessionToken
                    this.query = query
                }
                PhotoDemoEventResults(response.autocompletePredictions)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                PhotoDemoEventError(e)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PhotoDemoEventIdle
        )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        // Reset photo state when starting a new search
        _photoState.value = PhotoState()
    }

    fun onPredictionClicked(prediction: AutocompletePrediction) {
        viewModelScope.launch {
            _photoState.value = PhotoState(isLoading = true)
            try {
                val currentToken = sessionToken
                // 1. Fetch place details to get photo metadata
                val placeResponse = placesClient.awaitFetchPlace(
                    prediction.placeId,
                    listOf(Place.Field.PHOTO_METADATAS)
                ) {
                    sessionToken = currentToken
                }

                val metadata = placeResponse.place.photoMetadatas?.firstOrNull()
                if (metadata == null) {
                    _photoState.value = PhotoState(error = "No photo metadata found for this place.")
                    return@launch
                }

                // 2. Fetch the resolved photo URI
                // This is the improved API that returns a Uri directly.
                val photoResponse = placesClient.awaitFetchResolvedPhotoUri(metadata)
                _photoState.value = PhotoState(uri = photoResponse.uri)

                Log.d("PlacesPhotoViewModel", "Successfully fetched photo URI: ${photoResponse.uri}")
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                Log.e("PlacesPhotoViewModel", "Error fetching photo", e)
                _photoState.value = PhotoState(error = "Failed to fetch photo: ${e.message}")
            } finally {
                sessionToken = null
            }
        }
    }
}
