package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.CaretString
import org.junit.Assert
import org.junit.Test
import java.util.*

/**
 * Created by taflanidi on 03.02.17.
 */
class YearTest : MaskTest() {

    override fun format(): String {
        return "[0099]"
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
        Assert.assertEquals(placeholder, "0000")
    }

    @Test
    fun acceptableTextLength_allSet_returnsCorrectCount() {
        val acceptableTextLength: Int = this.mask().acceptableTextLength()
        Assert.assertEquals(acceptableTextLength, 2)
    }

    @Test
    fun totalTextLength_allSet_returnsCorrectCount() {
        val totalTextLength: Int = this.mask().totalTextLength()
        Assert.assertEquals(totalTextLength, 4)
    }

    @Test
    fun acceptableValueLength_allSet_returnsCorrectCount() {
        val acceptableValueLength: Int = this.mask().acceptableValueLength()
        Assert.assertEquals(acceptableValueLength, 2)
    }

    @Test
    fun totalValueLength_allSet_returnsCorrectCount() {
        val totalValueLength: Int = this.mask().totalValueLength()
        Assert.assertEquals(totalValueLength, 4)
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
        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun apply_112_returns_112() {
        val inputString = "112"
        val inputCaret: Int = inputString.length

        val expectedString = "112"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "112"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(3, result.affinity)
        Assert.assertEquals(true, result.complete)
    }

    @Test
    fun apply_1122_returns_1122() {
        val inputString = "1122"
        val inputCaret: Int = inputString.length

        val expectedString = "1122"
        val expectedCaret: Int = expectedString.length
        val expectedValue = "1122"

        val result: Mask.Result = this.mask().apply(CaretString(inputString, inputCaret, CaretString.CaretGravity.FORWARD(false)))

        Assert.assertEquals(expectedString, result.formattedText.string)
        Assert.assertEquals(expectedCaret, result.formattedText.caretPosition)
        Assert.assertEquals(expectedValue, result.extractedValue)

        Assert.assertEquals(4, result.affinity)
        Assert.assertEquals(true, result.complete)
    }

}