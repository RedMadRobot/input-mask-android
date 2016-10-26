package com.redmadrobot.inputmask.model.state

import com.redmadrobot.inputmask.model.Next
import com.redmadrobot.inputmask.model.State

/**
 * ### EOLState
 *
 * End-of-line state. Serves as mask format terminator character.
 *
 * Does not accept any character. Always returns ```self``` as the next state, ignoring the child
 * state given during initialization.
 *
 * @author taflanidi
 */
class EOLState : State(child = null) {
    override fun accept(character: Char): Next? {
        return null
    }

    override fun toString(): String {
        return "EOL"
    }
}