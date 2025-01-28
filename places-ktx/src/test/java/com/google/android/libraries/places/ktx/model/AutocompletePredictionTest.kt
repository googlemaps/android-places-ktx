/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.libraries.places.ktx.model

import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.ktx.api.model.autocompletePrediction
import org.junit.Assert.assertEquals
import org.junit.Test

internal class AutocompletePredictionTest {
    @Test
    fun testBuilder() {
        val prediction = autocompletePrediction("placeId") {
            placeTypes = listOf(Place.Type.AQUARIUM)
        }
        val res = prediction.getPrimaryText(null)
        assertEquals("placeId", prediction.placeId)
        assertEquals(listOf(Place.Type.AQUARIUM), prediction.placeTypes)
    }
}