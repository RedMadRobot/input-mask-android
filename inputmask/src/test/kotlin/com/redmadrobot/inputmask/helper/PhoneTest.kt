package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.CaretString
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by taflanidi on 10.11.16.
 */
class PhoneTest : MaskTest() {

    override fun format(): String {
        return "+7 ([000]) [000] [00] [00]"
    }

    @Test
    fun GetPlaceholder_allSet_returnsCorrectPlaceholder() {
        val placeholder: String = this.mask().placeholder()
        Assert.assertEquals(placeholder, "+7 (000) 000 00 00")
    }

    @Test
    fun AcceptableTextLength_allSet_returnsCorrectCount() {
        val acceptableTextLength: Int = this.mask().acceptableTextLength()
        Assert.assertEquals(acceptableTextLength, 18)
    }

    @Test
    fun TotalTextLength_allSet_returnsCorrectCount() {
        val totalTextLength: Int = this.mask().totalTextLength()
        Assert.assertEquals(totalTextLength, 18)
    }

    @Test
    fun AcceptableValueLength_allSet_returnsCorrectCount() {
        val acceptableValueLength: Int = this.mask().acceptableValueLength()
        Assert.assertEquals(acceptableValueLength, 10)
    }

    @Test
    fun TotalValueLength_allSet_returnsCorrectCount() {
        val totalValueLength: Int = this.mask().totalValueLength()
        Assert.assertEquals(totalValueLength, 10)
    }

    @Test
    fun Apply_plus_return_plus() {
        val inputString: String = "+"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7_return_plus7() {
        val inputString: String = "+7"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7space_return_plus7space() {
        val inputString: String = "+7 "
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 "
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace_return_plus7spaceBrace() {
        val inputString: String = "+7 ("
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace1_return_plus7spaceBrace1() {
        val inputString: String = "+7 (1"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (1"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace12_return_plus7spaceBrace12() {
        val inputString: String = "+7 (12"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (12"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "12"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123_return_plus7spaceBrace123() {
        val inputString: String = "+7 (123"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "123"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123brace_return_plus7spaceBrace123brace() {
        val inputString: String = "+7 (123)"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123)"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "123"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123braceSpace_return_plus7spaceBrace123braceSpace() {
        val inputString: String = "+7 (123) "
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) "
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "123"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123braceSpace4_return_plus7spaceBrace123braceSpace4() {
        val inputString: String = "+7 (123) 4"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 4"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1234"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123braceSpace45_return_plus7spaceBrace123braceSpace45() {
        val inputString: String = "+7 (123) 45"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 45"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "12345"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123braceSpace456_return_plus7spaceBrace123braceSpace456() {
        val inputString: String = "+7 (123) 456"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 456"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "123456"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123braceSpace456space_return_plus7spaceBrace123braceSpace456space() {
        val inputString: String = "+7 (123) 456 "
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 456 "
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "123456"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123braceSpace456space7_return_plus7spaceBrace123braceSpace456space7() {
        val inputString: String = "+7 (123) 456 7"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 456 7"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1234567"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123braceSpace456space78_return_plus7spaceBrace123braceSpace456space78() {
        val inputString: String = "+7 (123) 456 78"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 456 78"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "12345678"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123braceSpace456space78space_return_plus7spaceBrace123braceSpace456space78space() {
        val inputString: String = "+7 (123) 456 78 "
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 456 78 "
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "12345678"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123braceSpace456space78space9_return_plus7spaceBrace123braceSpace456space78space9() {
        val inputString: String = "+7 (123) 456 78 9"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 456 78 9"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "123456789"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_plus7spaceBrace123braceSpace456space78space90_return_plus7spaceBrace123braceSpace456space78space90() {
        val inputString: String = "+7 (123) 456 78 90"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 456 78 90"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1234567890"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun Apply_7_return_plus7() {
        val inputString: String = "7"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_9_return_plus7spaceBrace9() {
        val inputString: String = "9"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (9"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "9"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_1234567890_return_plus7spaceBrace123braceSpace456space78space90() {
        val inputString: String = "1234567890"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 456 78 90"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1234567890"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun Apply_12345678901_return_plus7spaceBrace123braceSpace456space78space90() {
        val inputString: String = "12345678901"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 456 78 90"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1234567890"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun Apply_plus1234567890_return_plus7spaceBrace123braceSpace456space78space90() {
        val inputString: String = "+1234567890"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 456 78 90"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1234567890"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun Apply_plusBrace123brace456dash78dash90_return_plus7spaceBrace123braceSpace456space78space90() {
        val inputString: String = "+(123)456-78-90"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (123) 456 78 90"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1234567890"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun ApplyAutocomplete_empty_return_plus7spaceBrace() {
        val inputString: String = ""
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), true)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun ApplyAutocomplete_plus_return_plus7spaceBrace() {
        val inputString: String = "+"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), true)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun ApplyAutocomplete_plus7_return_plus7spaceBrace() {
        val inputString: String = "+7"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), true)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun ApplyAutocomplete_plus7space_return_plus7spaceBrace() {
        val inputString: String = "+7 "
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), true)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun ApplyAutocomplete_plus7spaceBrace_return_plus7spaceBrace() {
        val inputString: String = "+7 ("
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), true)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun ApplyAutocomplete_a_return_plus7spaceBrace() {
        val inputString: String = "a"
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), true)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun ApplyAutocomplete_aPlus7spaceBrace_return_plus7spaceBrace() {
        val inputString: String = "a+7 ("
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 (7"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "7"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), true)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun ApplyAutocomplete_7space_return_plus7spaceBrace() {
        val inputString: String = "7 "
        val inputCaret: Int = inputString.length

        val expectedString: String = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), true)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

}
