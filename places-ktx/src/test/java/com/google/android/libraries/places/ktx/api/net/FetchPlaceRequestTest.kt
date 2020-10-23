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

import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import org.junit.Assert.assertEquals
import org.junit.Test

internal class FetchPlaceRequestTest {

    @Test
    fun testBuilderNoActions() {
        val request = fetchPlaceRequest(
            "placeId",
            listOf(Place.Field.NAME)
        )
        assertEquals("placeId", request.placeId)
        assertEquals(listOf(Place.Field.NAME), request.placeFields)
    }

    @Test
    fun testBuilderWithActions() {
        val cancellationToken = CancellationTokenSource().token
        val sessionToken = AutocompleteSessionToken.newInstance()
        val request = fetchPlaceRequest(
            "placeId",
            listOf(Place.Field.NAME)
        ) {
            setCancellationToken(cancellationToken)
            setSessionToken(sessionToken)
        }
        assertEquals("placeId", request.placeId)
        assertEquals(listOf(Place.Field.NAME), request.placeFields)
        assertEquals(cancellationToken, request.cancellationToken)
        assertEquals(sessionToken, request.sessionToken)
    }
}