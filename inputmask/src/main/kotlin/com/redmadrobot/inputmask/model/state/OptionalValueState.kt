package com.redmadrobot.inputmask.model.state

import com.redmadrobot.inputmask.model.Next
import com.redmadrobot.inputmask.model.State

/**
 * ### OptionalValueState
 *
 * Represents optional characters in square brackets [].
 *
 * Accepts any characters, but puts into the result string only the characters of own type
 * (see ```StateType```).
 *
 * Returns accepted characters of own type as an extracted value.
 *
 * @see ```OptionalValueState.StateType```
 *
 * @author taflanidi
 */
class OptionalValueState(child: State, type: StateType) : State(child) {

    val type: StateType

    init {
        this.type = type
    }

    private fun accepts(character: Char): Boolean {
        return when (this.type) {
            StateType.Numeric -> character.isDigit()
            StateType.Literal -> character.isLetter()
            StateType.AlphaNumeric -> character.isLetterOrDigit()
        }
    }

    override fun accept(character: Char): Next? {
        return if (this.accepts(character)) {
            Next(
                    this.nextState(),
                    character,
                    true,
                    character
            )
        } else {
            Next(
                    this.nextState(),
                    null,
                    false,
                    null
            )
        }
    }

    override fun toString(): String {
        return when (this.type) {
            StateType.Literal -> "[a] -> " + if (null == this.child) "null" else child.toString()
            StateType.Numeric -> "[9] -> " + if (null == this.child) "null" else child.toString()
            StateType.AlphaNumeric -> "[-] -> " + if (null == this.child) "null" else child.toString()
        }
    }
}