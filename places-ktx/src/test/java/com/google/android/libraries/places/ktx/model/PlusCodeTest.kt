package com.google.android.libraries.places.ktx.model

import com.google.android.libraries.places.ktx.api.model.plusCode
import org.junit.Assert.assertEquals
import org.junit.Test

class PlusCodeTest {

    @Test
    fun testBuilder() {
        val plusCode = plusCode {
            setCompoundCode("ABC")
            setGlobalCode("DEF")
        }
        assertEquals("ABC", plusCode.compoundCode)
        assertEquals("DEF", plusCode.globalCode)
    }
}