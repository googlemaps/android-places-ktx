package com.google.android.libraries.places.ktx.model

import com.google.android.libraries.places.ktx.api.model.photoMetadata
import org.junit.Assert.assertEquals
import org.junit.Test

class PhotoMetadataTest {

    @Test
    fun testBuilderNoActions() {
        val photoMetadata = photoMetadata("reference")
        assertEquals("", photoMetadata.attributions)
        assertEquals(0, photoMetadata.height)
        assertEquals(0, photoMetadata.width)
    }

    @Test
    fun testBuilderWithActions() {
        val photoMetadata = photoMetadata("reference") {
            setAttributions("attributions")
            setWidth(100)
            setHeight(100)
        }
        assertEquals("attributions", photoMetadata.attributions)
        assertEquals(100, photoMetadata.height)
        assertEquals(100, photoMetadata.width)
    }
}