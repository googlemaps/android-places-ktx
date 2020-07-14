package com.google.android.libraries.places.ktx.widget

import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

sealed class PlaceSelectionResult

data class PlaceSelectionSuccess(val place: Place) : PlaceSelectionResult()

data class PlaceSelectionError(val status: Status) : PlaceSelectionResult()

@ExperimentalCoroutinesApi
fun AutocompleteSupportFragment.placeSelectionEvents() : Flow<PlaceSelectionResult> =
    callbackFlow {
        this@placeSelectionEvents.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                offer(PlaceSelectionSuccess(place))
            }

            override fun onError(status: Status) {
                offer(PlaceSelectionError(status))
            }
        })
        awaitClose { this@placeSelectionEvents.setOnPlaceSelectedListener(null) }
    }
