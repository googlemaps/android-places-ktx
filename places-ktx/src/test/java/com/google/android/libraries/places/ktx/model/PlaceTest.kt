package com.google.android.libraries.places.ktx.model

import com.google.android.libraries.places.api.model.AddressComponents
import com.google.android.libraries.places.ktx.api.model.addressComponent
import com.google.android.libraries.places.ktx.api.model.place
import org.junit.Assert.assertEquals
import org.junit.Test

class PlaceTest {

    @Test
    fun testBuilder() {
        val place = place {
            setAddress("address")
            setAddressComponents(AddressComponents.newInstance(
                listOf(
                    addressComponent("Main Street", listOf("street_address")) {
                        setShortName("Main St.")
                    }
                )
            ))
        }
        assertEquals("address", place.address)
        assertEquals(AddressComponents.newInstance(
            listOf(
                addressComponent("Main Street", listOf("street_address")) {
                    setShortName("Main St.")
                }
            )
        ), place.addressComponents)
    }
}