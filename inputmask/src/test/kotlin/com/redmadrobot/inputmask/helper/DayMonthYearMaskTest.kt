package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.CaretString
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by taflanidi on 10.11.16.
 */
class DayMonthYearMaskTest : MaskTest() {

    override fun format(): String {
        return "[00]{.}[00]{.}[0000]"
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
            masks.add(Mask(this.format(), emptyList()))
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
        Assert.assertEquals(placeholder, "00.00.0000")
    }

    @Test
    fun acceptableTextLength_allSet_returnsCorrectCount() {
        val acceptableTextLength: Int = this.mask().acceptableTextLength()
        Assert.assertEquals(acceptableTextLength, 10)
    }

    @Test
    fun totalTextLength_allSet_returnsCorrectCount() {
        val totalTextLength: Int = this.mask().totalTextLength()
        Assert.assertEquals(totalTextLength, 10)
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
    fun apply_1_returns_1() {
        val inputString = "1"
        val inputCaret: Int = inputString.length

        val expectedString = "1"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(
            CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false))
        )

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_11_returns_11() {
        val inputString = "11"
        val inputCaret: Int = inputString.length

        val expectedString = "11"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(
            CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false))
        )

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_111_returns_11dot1() {
        val inputString = "111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.1"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(
            CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false))
        )

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_1111_returns_11dot11() {
        val inputString = "1111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11.11"

        val result: Mask.Result = this.mask().apply(
            CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false))
        )

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_123456_returns_12dot34dot56() {
        val inputString = "123456"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34.56"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(
            CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false))
        )

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_12dot3_returns_12dot3() {
        val inputString = "12.3"
        val inputCaret: Int = inputString.length

        val expectedString = "12.3"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(
            CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false))
        )

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_12dot34_returns_12dot34() {
        val inputString = "12.34"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_12dot34dot5_returns_12dot34dot5() {
        val inputString = "12.34.5"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34.5"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_12dot34dot56_returns_12dot34dot56() {
        val inputString = "12.34.56"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34.56"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_1234567_returns_12dot34dot567() {
        val inputString = "1234567"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34.567"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_12345678_returns_12dot34dot5678() {
        val inputString = "12345678"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34.5678"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun apply_1111_StartIndex_returns_11dot11_StartIndex() {
        val inputString = "1111"
        val inputCaret = 0

        val expectedString = "11.11"
        val expectedCaret = 0
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_1111_ThirdIndex_returns_11dot11_FourthIndex() {
        val inputString = "1111"
        val inputCaret = 2

        val expectedString = "11.11"
        val expectedCaret = 3
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_abc1111_returns_11dot11() {
        val inputString = "abc1111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_abc1de111_returns_11dot11() {
        val inputString = "abc1de111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_abc1de1fg11_returns_11dot11() {
        val inputString = "abc1de1fg11"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_empty_returns_empty() {
        val inputString = ""
        val inputCaret: Int = inputString.length

        val expectedString = ""
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_1_returns_1() {
        val inputString = "1"
        val inputCaret: Int = inputString.length

        val expectedString = "1"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_11_returns_11dot() {
        val inputString = "11"
        val inputCaret: Int = inputString.length

        val expectedString = "11."
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_111_returns_11dot1() {
        val inputString = "111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.1"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_1111_returns_11dot11dot() {
        val inputString = "1111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11."
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_11111_returns_11dot11dot1() {
        val inputString = "11111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11.1"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_111111_returns_11dot11dot11() {
        val inputString = "111111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11.11"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_1111111_returns_11dot11dot111() {
        val inputString = "1111111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11.111"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_11111111_returns_11dot11dot1111() {
        val inputString = "11111111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11.1111"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun applyAutocomplete_111111111_returns_11dot11dot1111() {
        val inputString = "111111111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11.1111"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

}
