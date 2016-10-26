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
        if (this.accepts(character)) {
            return Next(
                    this.nextState(),
                    character,
                    true,
                    character
            )
        } else {
            return Next(
                    this.nextState(),
                    null,
                    false,
                    null
            )
        }
    }

    override fun toString(): String {
        when (this.type) {
            StateType.Literal -> return "[a] -> " + if (null == this.child) "null" else child.toString()
            StateType.Numeric -> return "[9] -> " + if (null == this.child) "null" else child.toString()
            StateType.AlphaNumeric -> return "[-] -> " + if (null == this.child) "null" else child.toString()
        }
    }
}