package com.redmadrobot.inputmask.model.state

import com.redmadrobot.inputmask.model.Next
import com.redmadrobot.inputmask.model.State

/**
 * ### FreeState
 *
 * Represents "free" characters outside square and curly brackets.
 *
 * Accepts every character but does not put it into the result string, unless the character equals
 * the one from the mask format. If it's not, inserts the symbol from the mask format into the
 * result.
 *
 * Always returns ```nil``` as an extracted value, does not affect the resulting value.
 *
 * @author taflanidi
 */
class FreeState(child: State, ownCharacter: Char) : State(child) {
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
                    null
            )
        } else {
            return Next(
                    this.nextState(),
                    this.ownCharacter,
                    false,
                    null
            )
        }
    }

    override fun autocomplete(): Next? {
        return Next(
                this.nextState(),
                this.ownCharacter,
                false,
                null
        )
    }

    override fun toString(): String {
        return "" + this.ownCharacter + " -> " + if (null == this.child) "null" else child.toString()
    }
}
