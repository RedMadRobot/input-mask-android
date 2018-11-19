package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.State
import com.redmadrobot.inputmask.model.state.*
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

/**
 * @author: Fi5t
 */
class CompilerTest {

    @Test(expected = Compiler.FormatError::class)
    fun compile_BadMask_ThrowFormatError() {
        Compiler(emptyList()).compile("[00[9]9]")
    }

    @Test
    fun compile_ReturnsCorrectState() {
        val initialState = Compiler(emptyList()).compile("[09]{.}[09]{.}19[00]")

        var state: State? = initialState
        val stateList = ArrayList<State>(10)
        val correctnessList = ArrayList<Boolean>(10)

        while (null != state && state !is EOLState) {
            stateList.add(state)
            state = state.child
        }

        with(correctnessList) {
            add(stateList[0] is ValueState)
            add(stateList[1] is OptionalValueState)
            add(stateList[2] is FixedState)
            add(stateList[3] is ValueState)
            add(stateList[4] is OptionalValueState)
            add(stateList[5] is FixedState)
            add(stateList[6] is FreeState)
            add(stateList[7] is FreeState)
            add(stateList[8] is ValueState)
            add(stateList[9] is ValueState)
        }

        val isCorrectState = correctnessList.reduce { x, y -> x && y }

        assertTrue(isCorrectState)
    }
}