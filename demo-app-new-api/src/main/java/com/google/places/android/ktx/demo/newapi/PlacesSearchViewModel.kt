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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            delay(300)
            // Use SearchByText
            val response = placesClient.awaitSearchByText(query, placeFields) {
                setMaxResultCount(5)
            }
            _events.value = PlacesSearchEventFound(response.places)
        }
    }

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
            val circle = CircularBounds.newInstance(boulderCenter, 5000.0) // 5km radius
            
            val response = placesClient.awaitSearchNearby(circle, placeFields) {
                 setMaxResultCount(5)
                 setIncludedTypes(listOf(PlaceTypes.RESTAURANT))
            }
            _events.value = PlacesSearchEventFound(response.places)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onPlaceClicked(place: Place) {
        val handler = CoroutineExceptionHandler { _, e ->
            Log.e("PlacesSearchViewModel", "Error in details check", e)
        }
        viewModelScope.launch(handler) {
            // Check IsOpen
            try {
                val isOpenResponse = placesClient.awaitIsOpen(place)
                Log.d("PlacesSearchViewModel", "Place ${place.displayName} is open: ${isOpenResponse.isOpen}")
            } catch (e: Exception) {
                 Log.e("PlacesSearchViewModel", "IsOpen failed", e)
            }

            // Fetch Photo URI if photos exist
            val photoMetadata = place.photoMetadatas?.firstOrNull()
            if (photoMetadata != null) {
                try {
                   val photoUriResponse = placesClient.awaitFetchResolvedPhotoUri(photoMetadata)
                   Log.d("PlacesSearchViewModel", "Photo URI: ${photoUriResponse.uri}")
                } catch (e: Exception) {
                    Log.e("PlacesSearchViewModel", "FetchResolvedPhotoUri failed", e)
                }
            }
        }
    }

    companion object {
        val placeFields = listOf(
            Place.Field.ID,
            Place.Field.DISPLAY_NAME,
            Place.Field.FORMATTED_ADDRESS,
            Place.Field.LOCATION,
            Place.Field.PHOTO_METADATAS,
            Place.Field.OPENING_HOURS, // For IsOpen
            Place.Field.UTC_OFFSET // For IsOpen
        )
    }
}