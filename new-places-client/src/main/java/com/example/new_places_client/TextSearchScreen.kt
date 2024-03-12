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

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.ktx.api.net.awaitSearchByText
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

private const val TAG = "TextSearch"

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun TextSearchScreen(placesClient: PlacesClient, onShowMessage: (String) -> Unit) {
    // The list of fields to retrieve from the server
    // See the full list at https://developers.google.com/maps/documentation/places/android-sdk/place-data-fields
    val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)

    val scope = rememberCoroutineScope()

    // To keep the demo a bit simpler, use remembers.
    var searchText by rememberSaveable {
        mutableStateOf("")
    }

    var searchResults by rememberSaveable {
        mutableStateOf<List<PlaceDetails>>(emptyList())
    }

    var showSpinner by rememberSaveable {
        mutableStateOf(false)
    }

    var selectedPlace by rememberSaveable {
        mutableStateOf<PlaceDetails?>(null)
    }

    var markerStates by remember {
        mutableStateOf<List<MarkerState>>(emptyList())
    }

    var bounds by remember {
        mutableStateOf<LatLngBounds?>(null)
    }

    var center by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    // Perform a new search whenever the searchText is changed
    LaunchedEffect(key1 = searchText) {
        if (searchText.isEmpty()) {
            searchResults = emptyList()
            bounds = null
        } else {
            showSpinner = true
            try {
                val response = placesClient.awaitSearchByText(searchText, fields) {
                    maxResultCount = 10
                }

                searchResults = response.places.mapNotNull { place ->
                    place.toPlaceDetails()
                }

                val locations = searchResults.map { place -> place.location }

                markerStates = locations.map {
                    MarkerState(position = it)
                }

                bounds = locations.toLatLngBounds().also {
                    center = it.center
                }
            } catch (e: ApiException) {
                Log.e(TAG, "Exception during call to placesClient.awaitSearchByText")
                onShowMessage(e.message ?: "Unknown error")
                searchText = ""
            }

            showSpinner = false
        }
    }

    Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp)
    ) {

        SearchRow { newSearchText ->
            searchText = newSearchText
            keyboardController?.hide()
        }

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(center, 10f)
        }

        if (showSpinner) {
            BigSpinner()
        } else {
            val focusedPlace = selectedPlace

            if (searchResults.isNotEmpty()) {
                Card(
                    modifier = Modifier
                      .padding(top = 8.dp, bottom = 8.dp)
                      .weight(1f)
                ) {
                    SearchResultsList(searchResults, focusedPlace) { place, doubleClick ->
                        if (doubleClick) {
                            selectedPlace = place
                            scope.launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLngZoom(
                                        place.location,
                                        15f
                                    )
                                )
                            }
                        } else {
                            selectedPlace = if (selectedPlace == place) {
                                null
                            } else {
                                place
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier
                      .padding(top = 8.dp, bottom = 8.dp)
                      .weight(2f)
                ) {
                    if (focusedPlace != null) {
                        PlaceDetails(focusedPlace)
                    }

                    if (searchResults.isNotEmpty()) {
                        PlacesMap(cameraPositionState, center, searchResults, focusedPlace, bounds)
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchRow(onSearchClick: (String) -> Unit) {
    var searchText by rememberSaveable {
        mutableStateOf("grocery stores near the grand canyon")
    }

    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = searchText,
            onValueChange = { newText -> searchText = newText },
            singleLine = false,
            label = { Text(stringResource(R.string.places_text_search_hint)) },
            trailingIcon = {
                IconButton(onClick = { searchText = "" }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(R.string.clear_search)
                    )
                }
            }
        )
        Spacer(modifier = Modifier.width(8.dp))
        IconButton(
            modifier = Modifier.background(MaterialTheme.colorScheme.primary, CircleShape),
            onClick = { onSearchClick(searchText) }
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onPrimary,
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_action)
            )
        }
    }
}

@Composable
private fun PlacesMap(
    cameraPositionState: CameraPositionState,
    center: LatLng,
    searchResults: List<PlaceDetails>,
    focusedPlace: PlaceDetails?,
    bounds: LatLngBounds?,
) {
    val padding = with(LocalDensity.current) { 16.dp.toPx() }.toInt()

    val markerStates by remember(key1 = searchResults) {
        mutableStateOf(
            searchResults.associateWith { place ->
                MarkerState(position = place.location)
            }
        )
    }

    LaunchedEffect(key1 = focusedPlace, key2 = bounds, key3 = center) {
        val update = when {
            // If we have a focused place, center on that
            focusedPlace != null -> {
                CameraUpdateFactory.newLatLng(focusedPlace.location)
            }

            // Otherwise, show all of the places on the map
            bounds != null -> {
                CameraUpdateFactory.newLatLngBounds(bounds, padding)
            }

            // Fall-back to showing the "center"
            else -> {
                CameraUpdateFactory.newLatLng(center)
            }
        }

        cameraPositionState.animate(update)
    }

    LaunchedEffect(key1 = focusedPlace) {
        // If the focused place changes, show its info window
        if (focusedPlace != null) {
            markerStates[focusedPlace]?.showInfoWindow()
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        searchResults.forEach { place ->
            Marker(
                state = markerStates.getValue(place),
                title = place.name,
                snippet = place.address,
            )
        }
    }
}

@Composable
private fun PlaceDetails(focusedPlace: PlaceDetails) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = focusedPlace.name)
        Text(text = focusedPlace.address ?: stringResource(R.string.no_address_found))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SearchResultsList(
    searchResults: List<PlaceDetails>,
    focusedPlace: PlaceDetails?,
    onPlaceSelected: (PlaceDetails, Boolean) -> Unit,
) {
    LazyColumn {
        items(searchResults) { place ->
            Row(
                modifier = Modifier
                    .then(
                        if (place == focusedPlace)
                            Modifier.background(MaterialTheme.colorScheme.primaryContainer)
                        else
                            Modifier
                    )
                    .fillMaxWidth()
                    .padding(8.dp)
                    .combinedClickable(
                        onClick = { onPlaceSelected(place, false) },
                        onDoubleClick = { onPlaceSelected(place, true) }
                    ),
            ) {
                Text(place.name)
            }
        }
    }
}

// Creates a LatLngBounds object from a collection of LatLng objects
private fun Collection<LatLng>.toLatLngBounds(): LatLngBounds {
    if (isEmpty()) error("Cannot create a LatLngBounds from an empty list")

    return LatLngBounds.builder().apply {
        forEach { latLng -> this.include(latLng) }
    }.build()
}