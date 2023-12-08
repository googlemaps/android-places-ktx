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

package com.google.android.libraries.places.ktx.widget

import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

public sealed class PlaceSelectionResult

public data class PlaceSelectionSuccess(val place: Place) : PlaceSelectionResult()

public data class PlaceSelectionError(val status: Status) : PlaceSelectionResult()

public fun AutocompleteSupportFragment.placeSelectionEvents() : Flow<PlaceSelectionResult> =
    callbackFlow {
        this@placeSelectionEvents.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                trySend(PlaceSelectionSuccess(place))
            }

            override fun onError(status: Status) {
                trySend(PlaceSelectionError(status))
            }
        })
        awaitClose { this@placeSelectionEvents.setOnPlaceSelectedListener(null) }
    }
