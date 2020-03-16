package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.CaretString
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by taflanidi on 10.11.16.
 */
class DayMonthYearShortTest : MaskTest() {

    override fun format(): String {
        return "[90]{.}[90]{.}[0000]"
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
        Assert.assertEquals(acceptableTextLength, 8)
    }

    @Test
    fun totalTextLength_allSet_returnsCorrectCount() {
        val totalTextLength: Int = this.mask().totalTextLength()
        Assert.assertEquals(totalTextLength, 10)
    }

    @Test
    fun acceptableValueLength_allSet_returnsCorrectCount() {
        val acceptableValueLength: Int = this.mask().acceptableValueLength()
        Assert.assertEquals(acceptableValueLength, 8)
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
        val expectedValue = "1"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(1, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_11_returns_11() {
        val inputString = "11"
        val inputCaret: Int = inputString.length

        val expectedString = "11"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(2, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_111_returns_11dot1() {
        val inputString = "111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.1"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11.1"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(2, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_1111_returns_11dot11() {
        val inputString = "1111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11.11"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(3, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_123456_returns_12dot34dot56() {
        val inputString = "123456"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34.56"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "12.34.56"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(4, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_12dot3_returns_12dot3() {
        val inputString = "12.3"
        val inputCaret: Int = inputString.length

        val expectedString = "12.3"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "12.3"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(4, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_12dot34_returns_12dot34() {
        val inputString = "12.34"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "12.34"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(5, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_12dot34dot5_returns_12dot34dot5() {
        val inputString = "12.34.5"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34.5"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "12.34.5"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(7, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_12dot34dot56_returns_12dot34dot56() {
        val inputString = "12.34.56"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34.56"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "12.34.56"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(8, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_1234567_returns_12dot34dot567() {
        val inputString = "1234567"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34.567"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "12.34.567"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(5, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_12345678_returns_12dot34dot5678() {
        val inputString = "12345678"
        val inputCaret: Int = inputString.length

        val expectedString = "12.34.5678"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "12.34.5678"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(6, result.affinity)
        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun apply_1111_StartIndex_returns_11dot11_StartIndex() {
        val inputString = "1111"
        val inputCaret = 0

        val expectedString = "11.11"
        val expectedCaret = 0
        val expectedValue = "11.11"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(3, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_1111_ThirdIndex_returns_11dot11_FourthIndex() {
        val inputString = "1111"
        val inputCaret = 2

        val expectedString = "11.11"
        val expectedCaret = 3
        val expectedValue = "11.11"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(3, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_abc1111_returns_11dot11() {
        val inputString = "abc1111"
        val inputCaret: Int = inputString.length

        val expectedString = "11.11"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11.11"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(0, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_abc1de111_returns_11dot11() {
        val inputString = "abc1de111"
        val inputCaret: Int = inputString.length

        val expectedString = "1.11.1"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1.11.1"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(-4, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_abc1de1fg11_returns_11dot11() {
        val inputString = "abc1de1fg11"
        val inputCaret: Int = inputString.length

        val expectedString = "1.1.11"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1.1.11"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(-7, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun apply_a_returns_empty() {
        val inputString = "a"
        val inputCaret: Int = inputString.length

        val expectedString = ""
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = expectedString

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(-1, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_empty_returns_empty() {
        val inputString = ""
        val inputCaret: Int = inputString.length

        val expectedString = ""
        val expectedCaret: Int = expectedString.length
        val expectedValue = ""

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(0, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_1_returns_1() {
        val inputString = "1"
        val inputCaret: Int = inputString.length

        val expectedString = "1"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(1, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_11_returns_11dot() {
        val inputString = "11"
        val inputCaret: Int = inputString.length

        val expectedString = "11."
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11."

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(2, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_112_returns_11dot2() {
        val inputString = "112"
        val inputCaret: Int = inputString.length

        val expectedString = "11.2"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11.2"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(2, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_1122_returns_11dot22dot() {
        val inputString = "1122"
        val inputCaret: Int = inputString.length

        val expectedString = "11.22."
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11.22."

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(3, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_11223_returns_11dot22dot3() {
        val inputString = "11223"
        val inputCaret: Int = inputString.length

        val expectedString = "11.22.3"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11.22.3"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(3, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_112233_returns_11dot22dot33() {
        val inputString = "112233"
        val inputCaret: Int = inputString.length

        val expectedString = "11.22.33"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11.22.33"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(4, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_1122333_returns_11dot22dot333() {
        val inputString = "1122333"
        val inputCaret: Int = inputString.length

        val expectedString = "11.22.333"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11.22.333"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(5, result.affinity)
        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun applyAutocomplete_11223333_returns_11dot22dot3333() {
        val inputString = "11223333"
        val inputCaret: Int = inputString.length

        val expectedString = "11.22.3333"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11.22.3333"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(6, result.affinity)
        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun applyAutocomplete_112233334_returns_11dot22dot3333() {
        val inputString = "112233334"
        val inputCaret: Int = inputString.length

        val expectedString = "11.22.3333"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "11.22.3333"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(true)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(5, result.affinity)
        Assert.assertEquals(true, result.complete)
    }

}
