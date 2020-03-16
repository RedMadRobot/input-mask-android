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
    fun init_correctFormat_maskInitialized() {
        Assert.assertNotNull(this.mask())
    }

    @Test
    fun init_correctFormat_measureTime() {
        val startTime = System.nanoTime()

        val masks: MutableList<Mask> = ArrayList()
        for (i in 1..1000) {
            masks.add(Mask(this.format()))
        }
        val endTime = System.nanoTime()
        val duration = (endTime - startTime) / 1000000

        print("Took $duration milliseconds")
    }

    @Test
    fun getOrCreate_correctFormat_measureTime() {
        val startTime = System.nanoTime()

        val masks: MutableList<Mask> = ArrayList()
        for (i in 1..1000) {
            masks.add(Mask.getOrCreate(this.format(), emptyList()))
        }
        val endTime = System.nanoTime()
        val duration = (endTime - startTime) / 1000000

        print("Took $duration milliseconds")
    }

    @Test
    fun getPlaceholder_allSet_returnsCorrectPlaceholder() {
        val placeholder: String = this.mask().placeholder()
        Assert.assertEquals(placeholder, "+7 (000) 000 00 00")
    }

    @Test
    fun acceptableTextLength_allSet_returnsCorrectCount() {
        val acceptableTextLength: Int = this.mask().acceptableTextLength()
        Assert.assertEquals(acceptableTextLength, 18)
    }

    @Test
    fun totalTextLength_allSet_returnsCorrectCount() {
        val totalTextLength: Int = this.mask().totalTextLength()
        Assert.assertEquals(totalTextLength, 18)
    }

    @Test
    fun acceptableValueLength_allSet_returnsCorrectCount() {
        val acceptableValueLength: Int = this.mask().acceptableValueLength()
        Assert.assertEquals(acceptableValueLength, 10)
    }

    @Test
    fun totalValueLength_allSet_returnsCorrectCount() {
        val totalValueLength: Int = this.mask().totalValueLength()
        Assert.assertEquals(totalValueLength, 10)
    }

    @Test
    fun apply_plus_return_plus() {
        val inputString = "+"
        val inputCaret: Int = inputString.length

        val expectedString = "+"
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7_return_plus7() {
        val inputString = "+7"
        val inputCaret: Int = inputString.length

        val expectedString = "+7"
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7space_return_plus7space() {
        val inputString = "+7 "
        val inputCaret: Int = inputString.length

        val expectedString = "+7 "
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace_return_plus7spaceBrace() {
        val inputString = "+7 ("
        val inputCaret: Int = inputString.length

        val expectedString = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace1_return_plus7spaceBrace1() {
        val inputString = "+7 (1"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (1"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace12_return_plus7spaceBrace12() {
        val inputString = "+7 (12"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (12"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "12"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123_return_plus7spaceBrace123() {
        val inputString = "+7 (123"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "123"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123brace_return_plus7spaceBrace123brace() {
        val inputString = "+7 (123)"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123)"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "123"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123braceSpace_return_plus7spaceBrace123braceSpace() {
        val inputString = "+7 (123) "
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) "
        val expectedCaret: Int = expectedString.length
        val expectedValue = "123"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123braceSpace4_return_plus7spaceBrace123braceSpace4() {
        val inputString = "+7 (123) 4"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 4"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1234"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123braceSpace45_return_plus7spaceBrace123braceSpace45() {
        val inputString = "+7 (123) 45"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 45"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "12345"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123braceSpace456_return_plus7spaceBrace123braceSpace456() {
        val inputString = "+7 (123) 456"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 456"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "123456"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123braceSpace456space_return_plus7spaceBrace123braceSpace456space() {
        val inputString = "+7 (123) 456 "
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 456 "
        val expectedCaret: Int = expectedString.length
        val expectedValue = "123456"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123braceSpace456space7_return_plus7spaceBrace123braceSpace456space7() {
        val inputString = "+7 (123) 456 7"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 456 7"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1234567"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123braceSpace456space78_return_plus7spaceBrace123braceSpace456space78() {
        val inputString = "+7 (123) 456 78"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 456 78"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "12345678"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123braceSpace456space78space_return_plus7spaceBrace123braceSpace456space78space() {
        val inputString = "+7 (123) 456 78 "
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 456 78 "
        val expectedCaret: Int = expectedString.length
        val expectedValue = "12345678"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123braceSpace456space78space9_return_plus7spaceBrace123braceSpace456space78space9() {
        val inputString = "+7 (123) 456 78 9"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 456 78 9"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "123456789"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_plus7spaceBrace123braceSpace456space78space90_return_plus7spaceBrace123braceSpace456space78space90() {
        val inputString = "+7 (123) 456 78 90"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 456 78 90"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1234567890"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun apply_7_return_plus7() {
        val inputString = "7"
        val inputCaret: Int = inputString.length

        val expectedString = "+7"
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_9_return_plus7spaceBrace9() {
        val inputString = "9"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (9"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "9"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_1234567890_return_plus7spaceBrace123braceSpace456space78space90() {
        val inputString = "1234567890"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 456 78 90"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1234567890"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun apply_12345678901_return_plus7spaceBrace123braceSpace456space78space90() {
        val inputString = "12345678901"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 456 78 90"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1234567890"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun apply_plus1234567890_return_plus7spaceBrace123braceSpace456space78space90() {
        val inputString = "+1234567890"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 456 78 90"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1234567890"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun apply_plusBrace123brace456dash78dash90_return_plus7spaceBrace123braceSpace456space78space90() {
        val inputString = "+(123)456-78-90"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (123) 456 78 90"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1234567890"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun applyAutocomplete_empty_return_plus7spaceBrace() {
        val inputString = ""
        val inputCaret: Int = inputString.length

        val expectedString = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_plus_return_plus7spaceBrace() {
        val inputString = "+"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_plus7_return_plus7spaceBrace() {
        val inputString = "+7"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_plus7space_return_plus7spaceBrace() {
        val inputString = "+7 "
        val inputCaret: Int = inputString.length

        val expectedString = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_plus7spaceBrace_return_plus7spaceBrace() {
        val inputString = "+7 ("
        val inputCaret: Int = inputString.length

        val expectedString = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_a_return_plus7spaceBrace() {
        val inputString = "a"
        val inputCaret: Int = inputString.length

        val expectedString = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_aPlus7spaceBrace_return_plus7spaceBrace() {
        val inputString = "a+7 ("
        val inputCaret: Int = inputString.length

        val expectedString = "+7 (7"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "7"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_7space_return_plus7spaceBrace() {
        val inputString = "7 "
        val inputCaret: Int = inputString.length

        val expectedString = "+7 ("
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

}
