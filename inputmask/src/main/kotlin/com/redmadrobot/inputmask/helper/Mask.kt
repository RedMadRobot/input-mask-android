package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.CaretString
import com.redmadrobot.inputmask.model.Next
import com.redmadrobot.inputmask.model.Notation
import com.redmadrobot.inputmask.model.State
import com.redmadrobot.inputmask.model.state.*
import java.util.*

/**
 * ### Mask
 *
 * Iterates over user input. Creates formatted strings from it. Extracts value specified by mask
 * format.
 *
 * Provided mask format string is translated by the ```Compiler``` class into a set of states, which
 * define the formatting and value extraction.
 *
 * @see ```Compiler```, ```State``` and ```CaretString``` classes.
 *
 * @author taflanidi
 */
class Mask(format: String, private val customNotations: List<Notation>) {

    /**
     * Convenience constructor.
     */
    constructor(format: String): this(format, emptyList())

    /**
     * ### Result
     *
     * The end result of mask application to the user input string.
     */
    class Result(
        /**
         * Formatted text with updated caret position.
         */
        val formattedText: CaretString,
        /**
         * Value, extracted from formatted text according to mask format.
         */
        val extractedValue: String,
        /**
         * Calculated absolute affinity value between the mask format and input text.
         */
        val affinity: Int,
        /**
         * User input is complete.
         */
        val complete: Boolean
    )

    companion object Factory {
        private val cache: MutableMap<String, Mask> = HashMap()

        /**
         * Factory constructor.
         *
         * Operates over own ```Mask``` cache where initialized ```Mask``` objects are stored under
         * corresponding format key:
         * ```[format : mask]```
         *
         * @returns Previously cached ```Mask``` object for requested format string. If such it
         * doesn't exist in cache, the object is constructed, cached and returned.
         */
        fun getOrCreate(format: String, customNotations: List<Notation>): Mask {
            var cachedMask: Mask? = cache[format]
            if (null == cachedMask) {
                cachedMask = Mask(format, customNotations)
                cache[format] = cachedMask
            }
            return cachedMask
        }

        /**
         * Check your mask format is valid.
         *
         * @param format mask format.
         * @param customNotations a list of custom rules to compile square bracket ```[]``` groups of format symbols.
         *
         * @returns ```true``` if this format coupled with custom notations will compile into a working ```Mask``` object.
         * Otherwise ```false```.
         */
        fun isValid(format: String, customNotations: List<Notation>): Boolean {
            return try {
                Mask(format, customNotations)
                true
            } catch (e: Compiler.FormatError) {
                false
            }
        }
    }

    private val initialState: State = Compiler(this.customNotations).compile(format)

    /**
     * Apply mask to the user input string.
     *
     * @param text user input string with current cursor position
     *
     * @returns Formatted text with extracted value an adjusted cursor position.
     */
    fun apply(text: CaretString, autocomplete: Boolean): Result {
        val iterator = CaretStringIterator(text)

        var affinity = 0
        var extractedValue = ""
        var modifiedString = ""
        var modifiedCaretPosition: Int = text.caretPosition

        var state: State = this.initialState
        var beforeCaret: Boolean = iterator.beforeCaret()
        var character: Char? = iterator.next()

        while (null != character) {
            val next: Next? = state.accept(character)
            if (null != next) {
                state = next.state
                modifiedString += next.insert ?: ""
                extractedValue += next.value ?: ""
                if (next.pass) {
                    beforeCaret = iterator.beforeCaret()
                    character = iterator.next()
                    affinity += 1
                } else {
                    if (beforeCaret && null != next.insert) {
                        modifiedCaretPosition += 1
                    }
                    affinity -= 1
                }
            } else {
                if (iterator.beforeCaret()) {
                    modifiedCaretPosition -= 1
                }
                beforeCaret = iterator.beforeCaret()
                character = iterator.next()
                affinity -= 1
            }
        }

        while (autocomplete && beforeCaret) {
            val next: Next = state.autocomplete() ?: break
            state = next.state
            modifiedString += next.insert ?: ""
            extractedValue += next.value ?: ""
            if (null != next.insert) {
                modifiedCaretPosition += 1
            }
        }

        return Result(
            CaretString(
                modifiedString,
                modifiedCaretPosition
            ),
            extractedValue,
            affinity,
            this.noMandatoryCharactersLeftAfterState(state)
        )
    }

    /**
     * Generate placeholder.
     *
     * @return Placeholder string.
     */
    fun placeholder(): String = this.appendPlaceholder(this.initialState, "")

    /**
     * Minimal length of the text inside the field to fill all mandatory characters in the mask.
     *
     * @return Minimal satisfying count of characters inside the text field.
     */
    fun acceptableTextLength(): Int {
        var state: State? = this.initialState
        var length = 0

        while (null != state && state !is EOLState) {
            if (state is FixedState || state is FreeState || state is ValueState) {
                length += 1
            }
            state = state.child
        }

        return length
    }

    /**
     *  Maximal length of the text inside the field.
     *
     *  @return Total available count of mandatory and optional characters inside the text field.
     */
    fun totalTextLength(): Int {
        var state: State? = this.initialState
        var length = 0

        while (null != state && state !is EOLState) {
            if (state is FixedState || state is FreeState || state is ValueState || state is OptionalValueState) {
                length += 1
            }
            state = state.child
        }

        return length
    }

    /**
     * Minimal length of the extracted value with all mandatory characters filled.\
     *
     * @return Minimal satisfying count of characters in extracted value.
     */
    fun acceptableValueLength(): Int {
        var state: State? = this.initialState
        var length = 0

        while (null != state && state !is EOLState) {
            if (state is FixedState || state is ValueState) {
                length += 1
            }
            state = state.child
        }

        return length
    }

    /**
     * Maximal length of the extracted value.
     *
     * @return Total available count of mandatory and optional characters for extracted value.
     */
    fun totalValueLength(): Int {
        var state: State? = this.initialState
        var length = 0

        while (null != state && state !is EOLState) {
            if (state is FixedState || state is ValueState || state is OptionalValueState) {
                length += 1
            }
            state = state.child
        }

        return length
    }

    private fun appendPlaceholder(state: State?, placeholder: String): String {
        if (null == state) {
            return placeholder
        }

        if (state is EOLState) {
            return placeholder
        }

        if (state is FixedState) {
            return this.appendPlaceholder(state.child, placeholder + state.ownCharacter)
        }

        if (state is FreeState) {
            return this.appendPlaceholder(state.child, placeholder + state.ownCharacter)
        }

        if (state is OptionalValueState) {
            return when (state.type) {
                is OptionalValueState.StateType.AlphaNumeric -> {
                    this.appendPlaceholder(state.child, placeholder + "-")
                }

                is OptionalValueState.StateType.Literal -> {
                    this.appendPlaceholder(state.child, placeholder + "a")
                }

                is OptionalValueState.StateType.Numeric -> {
                    this.appendPlaceholder(state.child, placeholder + "0")
                }

                is OptionalValueState.StateType.Custom -> {
                    this.appendPlaceholder(state.child, placeholder + state.type.character)
                }
            }
        }

        if (state is ValueState) {
            return when (state.type) {
                is ValueState.StateType.AlphaNumeric -> {
                    this.appendPlaceholder(state.child, placeholder + "-")
                }

                is ValueState.StateType.Literal -> {
                    this.appendPlaceholder(state.child, placeholder + "a")
                }

                is ValueState.StateType.Numeric -> {
                    this.appendPlaceholder(state.child, placeholder + "0")
                }

                is ValueState.StateType.Ellipsis -> placeholder

                is ValueState.StateType.Custom -> {
                    this.appendPlaceholder(state.child, placeholder + state.type.character)
                }
            }
        }

        return placeholder
    }

    private fun noMandatoryCharactersLeftAfterState(state: State): Boolean {
        return if (state is EOLState) {
            true
        } else if (state is ValueState) {
            return state.isElliptical
        } else if (state is FixedState) {
            false
        } else {
            this.noMandatoryCharactersLeftAfterState(state.nextState())
        }
    }
}
