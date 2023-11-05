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
import com.google.android.libraries.places.ktx.api.model.openingHours
import com.google.android.libraries.places.ktx.api.model.period
import org.junit.Assert.assertEquals
import org.junit.Test

internal class OpeningHoursTest {

    @Test
    fun testBuilder() {

        val openingHours = openingHours {
            periods = listOf(
                period {
                    close = TimeOfWeek.newInstance(
                        DayOfWeek.MONDAY,
                        LocalTime.newInstance(0, 0)
                    )
                }
            )
            weekdayText = listOf("Monday")
        }

        assertEquals(
            listOf(
                period {
                    setClose(TimeOfWeek.newInstance(
                        DayOfWeek.MONDAY,
                        LocalTime.newInstance(0, 0)
                    ))
                }
            ),
            openingHours.periods
        )
        assertEquals(listOf("Monday"), openingHours.weekdayText)
    }
}