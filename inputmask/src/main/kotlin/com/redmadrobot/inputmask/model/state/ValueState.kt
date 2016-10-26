package com.redmadrobot.inputmask.model.state

import com.redmadrobot.inputmask.model.Next
import com.redmadrobot.inputmask.model.State

/**
 * ### ValueState
 *
 * Represents mandatory characters in square brackets [].
 *
 * Accepts only characters of own type (see ```StateType```). Puts accepted characters into the
 * result string.
 *
 * Returns accepted characters as an extracted value.
 *
 * @see ```ValueState.StateType```
 *
 * @author taflanidi
 */
class ValueState(child: State, type: StateType) : State(child) {

    val type: StateType

    enum class StateType {
        Numeric,
        Literal,
        AlphaNumeric
    }

    init {
        this.type = type
    }

    private fun accepts(character: Char): Boolean {
        when (this.type) {
            StateType.Numeric -> return character.isDigit()
            StateType.Literal -> return character.isLetter()
            StateType.AlphaNumeric -> return character.isLetterOrDigit()
        }
    }

    override fun accept(character: Char): Next? {
        if (!this.accepts(character))
            return null

        return Next(
                this.nextState(),
                character,
                true,
                character
        )
    }

    override fun toString(): String {
        when (this.type) {
            StateType.Literal -> return "[A] -> " + if (null == this.child) "null" else child.toString()
            StateType.Numeric -> return "[0] -> " + if (null == this.child) "null" else child.toString()
            StateType.AlphaNumeric -> return "[_] -> " + if (null == this.child) "null" else child.toString()
        }
    }

}