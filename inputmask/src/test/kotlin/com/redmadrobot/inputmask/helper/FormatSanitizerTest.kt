package com.redmadrobot.inputmask.helper

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author: Fi5t
 */
class FormatSanitizerTest {

    @Test(expected = Compiler.FormatError::class)
    fun sanitize_BadMask_ThrowFormatError() {
        FormatSanitizer().sanitize("+7 ([0[0]0]) [000]-[00]-[00]")
    }

    @Test
    fun sanitize_ReturnsSanitizedString() {
        val expectedString = "[0099]"
        val actualString = FormatSanitizer().sanitize("[0909]")

        assertEquals(expectedString, actualString)
    }

    @Test
    fun sanitize_DivideMixedSymbolsIntoOwnGroups() {
        val expectedString = "[09][Aa][_-]"
        val actualString = FormatSanitizer().sanitize("[09Aa_-]")

        assertEquals(expectedString, actualString)
    }

}
