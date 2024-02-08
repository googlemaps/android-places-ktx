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

import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.net.FetchPhotoRequest

/**
 * Builds a new [FetchPhotoRequest].
 *
 * @param photoMetadata the metadata for the requested photo
 * @param actions the actions to apply to the [FetchPhotoRequest.Builder]
 *
 * @return the constructed [FetchPhotoRequest]
 */
public fun fetchPhotoRequest(
    photoMetadata: PhotoMetadata,
    actions: (FetchPhotoRequest.Builder.() -> Unit)? = null
): FetchPhotoRequest {
    return FetchPhotoRequest.builder(photoMetadata).also { builder ->
        actions?.let { builder.apply(it) }
    }.build()
}
