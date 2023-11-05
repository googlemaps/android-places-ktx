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
import com.google.android.libraries.places.ktx.api.model.photoMetadata
import org.junit.Assert.assertEquals
import org.junit.Test

internal class FetchPhotoRequestTest {

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
            maxHeight = 100
            maxWidth = 100
            setCancellationToken(cancellationToken)
        }
        assertEquals(photoMetadata, request.photoMetadata)
        assertEquals(100, request.maxHeight)
        assertEquals(100, request.maxWidth)
        assertEquals(cancellationToken, request.cancellationToken)
    }
}