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

import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.IsOpenRequest

/**
 * Builds a new [IsOpenRequest].
 *
 * @param place the place to check
 * @param actions the actions to apply to the [IsOpenRequest.Builder]
 *
 * @return the constructed [IsOpenRequest]
 */
@Deprecated(
    "Use the version in the Places SDK instead.",
    ReplaceWith("isOpenRequest(place, actions)", "com.google.android.libraries.places.api.net.kotlin.isOpenRequest")
)
public fun isOpenRequest(
    place: Place,
    actions: (IsOpenRequest.Builder.() -> Unit)? = null
): IsOpenRequest {
    return IsOpenRequest.builder(place).also { builder ->
        actions?.let { builder.apply(it) }
    }.build()
}

/**
 * Builds a new [IsOpenRequest].
 *
 * @param placeId the ID of the place to check
 * @param actions the actions to apply to the [IsOpenRequest.Builder]
 *
 * @return the constructed [IsOpenRequest]
 */
@Deprecated(
    "Use the version in the Places SDK instead.",
    ReplaceWith("isOpenRequest(placeId, actions)", "com.google.android.libraries.places.api.net.kotlin.isOpenRequest")
)
public fun isOpenRequest(
    placeId: String,
    actions: (IsOpenRequest.Builder.() -> Unit)? = null
): IsOpenRequest {
    return IsOpenRequest.builder(placeId).also { builder ->
        actions?.let { builder.apply(it) }
    }.build()
}