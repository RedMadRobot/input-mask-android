package com.redmadrobot.inputmask.model.state

import com.redmadrobot.inputmask.model.Next
import com.redmadrobot.inputmask.model.State

/**
 * ### FixedState
 *
 * Represents characters in curly braces {}.
 *
 * Accepts every character but does not put it into the result string, unless the character equals
 * the one from the mask format. If it's not, inserts the symbol from the mask format into the
 * result.
 *
 * Always returns self as an extracted value.
 * @author taflanidi
 */
class FixedState(child: State?, ownCharacter: Char) : State(child) {
    val ownCharacter: Char

    init {
        this.ownCharacter = ownCharacter
    }

    override fun accept(character: Char): Next? {
        if (this.ownCharacter == character) {
            return Next(
                    this.nextState(),
                    character,
                    true,
                    character
            )
        } else {
            return Next(
                    this.nextState(),
                    this.ownCharacter,
                    false,
                    this.ownCharacter
            )
        }
    }

    override fun autocomplete(): Next? {
        return Next(
                this.nextState(),
                this.ownCharacter,
                false,
                this.ownCharacter
        )
    }

    override fun toString(): String {
        return "{" + this.ownCharacter + "} -> " + if (null == this.child) "null" else child.toString()
    }
}