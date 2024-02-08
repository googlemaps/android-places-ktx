// Copyright 2024 Google LLC
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
 * @param placeId The [Place.id] of the place for which isOpen is to be determined.
 * @param utcTimeMillis The milliseconds from 1970-01-01T00:00:00Z.
 * @param actions the actions to apply to the [IsOpenRequest.Builder]
 * @return the constructed [IsOpenRequest]
 */
public fun isOpenRequest(
  placeId: String,
  utcTimeMillis: Long? = null,
  actions: (IsOpenRequest.Builder.() -> Unit)? = null,
): IsOpenRequest {
  return if (utcTimeMillis == null) {
    IsOpenRequest.builder(placeId)
  } else {
    IsOpenRequest.builder(placeId, utcTimeMillis)
  }.also { request ->
    actions?.let { request.apply(it) }
  }.build()
}

/**
 * Builds a new [IsOpenRequest].
 *
 * @param place The [Place] for which isOpen is to be determined.
 * @param utcTimeMillis The milliseconds from 1970-01-01T00:00:00Z.
 * @param actions the actions to apply to the [IsOpenRequest.Builder]
 * @return the constructed [IsOpenRequest]
 * @throws IllegalArgumentException if [Place] does not have a [Place.id] associated with it.
 */
public fun isOpenRequest(
  place: Place,
  utcTimeMillis: Long? = null,
  actions: (IsOpenRequest.Builder.() -> Unit)? = null,
): IsOpenRequest {
  return if (utcTimeMillis == null) {
    IsOpenRequest.builder(place)
  } else {
    IsOpenRequest.builder(place, utcTimeMillis)
  }.also { request ->
    actions?.let { request.apply(it) }
  }.build()
}