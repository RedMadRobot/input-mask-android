package com.redmadrobot.inputmask.model

/**
 * ### CaretString
 *
 * Model object that represents string with current cursor position.
 *
 * @author taflanidi
 */
class CaretString {
    val string: String
    val caretPosition: Int

    constructor(string: String, caretPosition: Int) {
        this.string = string
        this.caretPosition = caretPosition
    }
}