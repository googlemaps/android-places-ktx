// Copyright 2026 Google LLC
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

import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.net.FetchResolvedPhotoUriRequest
import com.google.android.libraries.places.api.net.kotlin.fetchResolvedPhotoUriRequest as sdkFetchResolvedPhotoUriRequest

/**
 * Builds a new [FetchResolvedPhotoUriRequest].
 *
 * @param photoMetadata the metadata for the requested photo
 * @param actions the actions to apply to the [FetchResolvedPhotoUriRequest.Builder]
 *
 * @return the constructed [FetchResolvedPhotoUriRequest]
 */
@Deprecated(
    "Use the version in the Places SDK instead.",
    ReplaceWith("fetchResolvedPhotoUriRequest(photoMetadata, actions)", "com.google.android.libraries.places.api.net.kotlin.fetchResolvedPhotoUriRequest")
)
public fun fetchResolvedPhotoUriRequest(
    photoMetadata: PhotoMetadata,
    actions: (FetchResolvedPhotoUriRequest.Builder.() -> Unit)? = null
): FetchResolvedPhotoUriRequest {
    return sdkFetchResolvedPhotoUriRequest(photoMetadata, actions ?: {})
}
