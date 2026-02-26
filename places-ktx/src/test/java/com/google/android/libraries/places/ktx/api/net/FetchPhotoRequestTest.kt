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
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.net.FetchResolvedPhotoUriRequest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mockStatic

internal class FetchPhotoRequestTest {



    @Test
    fun testBuilderNoActions() {
        val photoMetadata = mock<PhotoMetadata>()
        val mockBuilder = mock<FetchResolvedPhotoUriRequest.Builder>()
        val mockRequest = mock<FetchResolvedPhotoUriRequest>()

        whenever(mockBuilder.build()).thenReturn(mockRequest)
        whenever(mockRequest.photoMetadata).thenReturn(photoMetadata)

        mockStatic(FetchResolvedPhotoUriRequest::class.java).use { mockedStatic ->
            mockedStatic.`when`<FetchResolvedPhotoUriRequest.Builder> {
                FetchResolvedPhotoUriRequest.builder(photoMetadata)
            }.thenReturn(mockBuilder)

            val request = fetchResolvedPhotoUriRequest(photoMetadata)
            assertEquals(photoMetadata, request.photoMetadata)
        }
    }

    @Test
    fun testBuilderWithActions() {
        val photoMetadata = mock<PhotoMetadata>()
        val mockBuilder = mock<FetchResolvedPhotoUriRequest.Builder>()
        val mockRequest = mock<FetchResolvedPhotoUriRequest>()
        val cancellationToken = CancellationTokenSource().token

        whenever(mockBuilder.setCancellationToken(cancellationToken)).thenReturn(mockBuilder)
        whenever(mockBuilder.build()).thenReturn(mockRequest)
        whenever(mockRequest.photoMetadata).thenReturn(photoMetadata)
        whenever(mockRequest.cancellationToken).thenReturn(cancellationToken)

        mockStatic(FetchResolvedPhotoUriRequest::class.java).use { mockedStatic ->
            mockedStatic.`when`<FetchResolvedPhotoUriRequest.Builder> {
                FetchResolvedPhotoUriRequest.builder(photoMetadata)
            }.thenReturn(mockBuilder)

            val request = fetchResolvedPhotoUriRequest(photoMetadata) {
                setCancellationToken(cancellationToken)
            }
            assertEquals(photoMetadata, request.photoMetadata)
            assertEquals(cancellationToken, request.cancellationToken)
        }
    }
}