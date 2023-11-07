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

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.model.RectangularBounds
import org.junit.Assert.assertEquals
import org.junit.Test

internal class FindAutocompletePredictionsRequestTest {

    @Test
    fun testBuilder() {
        val cancellationToken = CancellationTokenSource().token
        val request = findAutocompletePredictionsRequest {
            setCancellationToken(cancellationToken)
            setCountries("USA")
            locationBias = RectangularBounds.newInstance(LatLng(1.0,1.0), LatLng(2.0, 2.0))
            typesFilter = listOf(PlaceTypes.ESTABLISHMENT)
            query = "query"
        }
        assertEquals(cancellationToken, request.cancellationToken)
        assertEquals(listOf("USA"), request.countries)
        assertEquals(
            RectangularBounds.newInstance(LatLng(1.0,1.0), LatLng(2.0, 2.0)),
            request.locationBias
        )
        assertEquals(listOf(PlaceTypes.ESTABLISHMENT), request.typesFilter)
        assertEquals("query", request.query)
    }
}