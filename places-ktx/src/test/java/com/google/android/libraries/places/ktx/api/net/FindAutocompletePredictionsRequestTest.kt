package com.google.android.libraries.places.ktx.api.net

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import org.junit.Assert.assertEquals
import org.junit.Test

class FindAutocompletePredictionsRequestTest {

    @Test
    fun testBuilder() {
        val cancellationToken = CancellationTokenSource().token
        val request = findAutocompletePredictionsRequest {
            setCancellationToken(cancellationToken)
            setCountries("USA")
            setLocationBias(RectangularBounds.newInstance(LatLng(1.0,1.0), LatLng(2.0, 2.0)))
            setTypeFilter(TypeFilter.ESTABLISHMENT)
            setQuery("query")
        }
        assertEquals(cancellationToken, request.cancellationToken)
        assertEquals(listOf("USA"), request.countries)
        assertEquals(
            RectangularBounds.newInstance(LatLng(1.0,1.0), LatLng(2.0, 2.0)),
            request.locationBias
        )
        assertEquals(TypeFilter.ESTABLISHMENT, request.typeFilter)
        assertEquals("query", request.query)
    }
}