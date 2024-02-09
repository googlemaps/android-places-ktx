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

package com.google.android.libraries.places.ktx.api.model

import com.google.android.libraries.places.api.model.AddressComponent

/**
 * Builds a new [AddressComponent].
 *
 * @param name the name of this address component
 * @param types the types of this address component
 * @param actions the actions to apply to the [AddressComponent.Builder]
 * @see <a href="https://developers.google.com/maps/documentation/geocoding/intro#Types">Address types</a>
 *
 * @exception IllegalStateException if [name] or [types] is empty
 *
 * @return the constructed [AddressComponent]
 */
public fun addressComponent(
    name: String,
    types: List<String>,
    actions: (AddressComponent.Builder.() -> Unit)? = null
): AddressComponent {
    return AddressComponent.builder(name, types).also { builder ->
        actions?.let { builder.apply(it) }
    }.build()
}
