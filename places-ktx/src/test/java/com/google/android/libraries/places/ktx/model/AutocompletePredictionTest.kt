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