package com.google.android.libraries.places.ktx.model

import com.google.android.libraries.places.api.model.DayOfWeek
import com.google.android.libraries.places.api.model.LocalTime
import com.google.android.libraries.places.api.model.TimeOfWeek
import com.google.android.libraries.places.ktx.api.model.openingHours
import com.google.android.libraries.places.ktx.api.model.period
import org.junit.Assert.assertEquals
import org.junit.Test

class OpeningHoursTest {

    @Test
    fun testBuilder() {

        val openingHours = openingHours {
            setPeriods(
                listOf(
                    period {
                        setClose(
                            TimeOfWeek.newInstance(
                                DayOfWeek.MONDAY,
                                LocalTime.newInstance(0, 0)
                            )
                        )
                    }
                )
            )
            setWeekdayText(listOf("Monday"))
        }

        assertEquals(
            listOf(
                period {
                    setClose(
                        TimeOfWeek.newInstance(
                            DayOfWeek.MONDAY,
                            LocalTime.newInstance(0, 0)
                        )
                    )
                }
            ),
            openingHours.periods
        )
        assertEquals(listOf("Monday"), openingHours.weekdayText)
    }
}