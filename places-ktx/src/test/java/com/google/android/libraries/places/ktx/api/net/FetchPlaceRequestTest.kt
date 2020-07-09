package com.google.android.libraries.places.ktx.api.net

import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import org.junit.Assert.assertEquals
import org.junit.Test

class FetchPlaceRequestTest {

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