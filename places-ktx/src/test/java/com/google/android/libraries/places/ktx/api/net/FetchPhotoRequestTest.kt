package com.google.android.libraries.places.ktx.api.net

import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.ktx.api.model.photoMetadata
import org.junit.Assert.assertEquals
import org.junit.Test

class FetchPhotoRequestTest {

    @Test
    fun testBuilderNoActions() {
        val photoMetadata = photoMetadata("reference")
        val request = fetchPhotoRequest(photoMetadata)
        assertEquals(photoMetadata, request.photoMetadata)
    }

    @Test
    fun testBuilderWithActions() {
        val photoMetadata = photoMetadata("reference")
        val cancellationToken = CancellationTokenSource().token
        val request = fetchPhotoRequest(photoMetadata) {
            setMaxHeight(100)
            setMaxWidth(100)
            setCancellationToken(cancellationToken)
        }
        assertEquals(photoMetadata, request.photoMetadata)
        assertEquals(100, request.maxHeight)
        assertEquals(100, request.maxWidth)
        assertEquals(cancellationToken, request.cancellationToken)
    }
}