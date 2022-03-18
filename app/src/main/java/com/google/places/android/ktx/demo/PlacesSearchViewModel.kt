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

package com.google.places.android.ktx.demo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.LocationBias
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.ktx.api.net.awaitFetchPlace
import com.google.android.libraries.places.ktx.api.net.awaitFindAutocompletePredictions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PlacesSearchViewModel @Inject constructor(
    private val placesClient: PlacesClient
) : ViewModel() {
    private val _events = MutableLiveData<PlacesSearchEvent>()
    val events: LiveData<PlacesSearchEvent> = _events

    private var searchJob: Job? = null

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onSearchQueryChanged(query: String) {
        searchJob?.cancel()

        _events.value = PlacesSearchEventLoading

        val handler = CoroutineExceptionHandler { _, throwable ->
            _events.value = PlacesSearchEventError(throwable)
        }
        searchJob = viewModelScope.launch(handler) {
            // Add delay so that network call is performed only after there is a 300 ms pause in the
            // search query. This prevents network calls from being invoked if the user is still
            // typing.
            delay(300)

            val bias: LocationBias = RectangularBounds.newInstance(
                LatLng(37.7576948, -122.4727051), // SW lat, lng
                LatLng(37.808300, -122.391338) // NE lat, lng
            )

            val response = placesClient
                .awaitFindAutocompletePredictions {
                    locationBias = bias
                    typeFilter = TypeFilter.ESTABLISHMENT
                    this.query = query
                    countries = listOf("US")
                }

            _events.value = PlacesSearchEventFound(response.autocompletePredictions)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onAutocompletePredictionClicked(prediction: AutocompletePrediction) {
        viewModelScope.launch {
            try {
                val place = placesClient.awaitFetchPlace(
                    prediction.placeId,
                    listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.OPENING_HOURS)
                )
                Log.d("PlacesSearchViewModel", "Got place $place")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("PlacesSearchViewModel", e.message, e)
            }
        }
    }
}