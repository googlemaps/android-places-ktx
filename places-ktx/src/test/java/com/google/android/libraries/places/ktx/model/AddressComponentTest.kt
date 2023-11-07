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

package com.google.android.libraries.places.ktx.model

import com.google.android.libraries.places.ktx.api.model.addressComponent
import org.junit.Assert.assertEquals
import org.junit.Test

internal class AddressComponentTest {

    @Test
    fun testBuilderNoShortName() {
        val component = addressComponent(
            "Main Street",
            listOf("street_address")
        )
        assertEquals("Main Street", component.name)
        assertEquals(listOf("street_address"), component.types)
    }

    @Test
    fun testBuilderWithShortName() {
        val component = addressComponent(
            "Main Street",
            listOf("street_address")
        ) {
            shortName = "Main St."
        }
        assertEquals("Main Street", component.name)
        assertEquals("Main St.", component.shortName)
        assertEquals(listOf("street_address"), component.types)
    }
}