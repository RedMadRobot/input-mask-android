package com.redmadrobot.inputmask.model

/**
 * ### CaretString
 *
 * Model object that represents string with current cursor position.
 *
 * @author taflanidi
 */
data class CaretString(
    val string: String,
    val caretPosition: Int,
    val caretGravity: CaretGravity = CaretGravity.FORWARD
) {
    fun reversed() =
        CaretString(
            this.string.reversed(),
            this.string.length - this.caretPosition,
            this.caretGravity
        )

    enum class CaretGravity {
        FORWARD,
        BACKWARD
    }
}
