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

import com.google.android.libraries.places.api.model.DayOfWeek
import com.google.android.libraries.places.api.model.LocalTime
import com.google.android.libraries.places.api.model.TimeOfWeek
import com.google.android.libraries.places.ktx.api.model.period
import org.junit.Assert.assertEquals
import org.junit.Test

internal class PeriodTest {

    @Test
    fun testBuilder() {
        val period = period {
            close = TimeOfWeek.newInstance(
                DayOfWeek.MONDAY,
                LocalTime.newInstance(0, 0)
            )
            open = TimeOfWeek.newInstance(
                DayOfWeek.TUESDAY,
                LocalTime.newInstance(0, 0)
            )
        }
        assertEquals(
            TimeOfWeek.newInstance(
                DayOfWeek.MONDAY,
                LocalTime.newInstance(0, 0)
            ),
            period.close
        )
        assertEquals(
            TimeOfWeek.newInstance(
                DayOfWeek.TUESDAY,
                LocalTime.newInstance(0, 0)
            ),
            period.open
        )
    }
}