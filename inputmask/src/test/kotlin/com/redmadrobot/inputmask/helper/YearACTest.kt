package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.CaretString
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by taflanidi on 09.01.17.
 */
class YearACTest : MaskTest() {

    override fun format(): String {
        return "[9990] AC"
    }

    @Test
    fun GetPlaceholder_allSet_returnsCorrectPlaceholder() {
        val placeholder: String = this.mask().placeholder()
        Assert.assertEquals(placeholder, "0000 AC")
    }

    @Test
    fun AcceptableTextLength_allSet_returnsCorrectCount() {
        val acceptableTextLength: Int = this.mask().acceptableTextLength()
        Assert.assertEquals(acceptableTextLength, 4)
    }

    @Test
    fun TotalTextLength_allSet_returnsCorrectCount() {
        val totalTextLength: Int = this.mask().totalTextLength()
        Assert.assertEquals(totalTextLength, 7)
    }

    @Test
    fun AcceptableValueLength_allSet_returnsCorrectCount() {
        val acceptableValueLength: Int = this.mask().acceptableValueLength()
        Assert.assertEquals(acceptableValueLength, 1)
    }

    @Test
    fun TotalValueLength_allSet_returnsCorrectCount() {
        val totalValueLength: Int = this.mask().totalValueLength()
        Assert.assertEquals(totalValueLength, 4)
    }

    @Test
    fun Apply_1_return_1() {
        val inputString: String = "1"
        val inputCaret: Int = inputString.length

        val expectedString: String = "1"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_11_return_11() {
        val inputString: String = "11"
        val inputCaret: Int = inputString.length

        val expectedString: String = "11"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "11"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_111_return_111() {
        val inputString: String = "111"
        val inputCaret: Int = inputString.length

        val expectedString: String = "111"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "111"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_1111_return_1111() {
        val inputString: String = "1111"
        val inputCaret: Int = inputString.length

        val expectedString: String = "1111"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1111"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(false, result.complete)
    }

    @Test
    fun Apply_11112_return_1111spaceAC() {
        val inputString: String = "11112"
        val inputCaret: Int = inputString.length

        val expectedString: String = "1111 AC"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1111"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), false)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun ApplyAutocomplete_1111_return_1111spaceAC() {
        val inputString: String = "1111"
        val inputCaret: Int = inputString.length

        val expectedString: String = "1111 AC"
        val expectedCaret: Int = expectedString.length
        val expectedValue: String = "1111"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret), true)

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(true, result.complete)
    }

}
