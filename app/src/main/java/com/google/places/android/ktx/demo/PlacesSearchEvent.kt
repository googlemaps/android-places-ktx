package com.google.places.android.ktx.demo

import com.google.android.libraries.places.api.model.AutocompletePrediction

sealed class PlacesSearchEvent

object PlacesSearchEventLoading : PlacesSearchEvent()

data class PlacesSearchEventError(
    val exception: Throwable
) : PlacesSearchEvent()

data class PlacesSearchEventFound(
    val places: List<AutocompletePrediction>
) : PlacesSearchEvent()
