package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.CaretString
import com.redmadrobot.inputmask.model.Next
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
class Mask(format: String) {

    /**
     * ### Result
     *
     * The end result of mask application to the user input string.
     */
    class Result(
        formattedText: CaretString,
        extractedValue: String,
        affinity: Int,
        complete: Boolean
    ) {

        /**
         * Formatted text with updated caret position.
         */
        val formattedText: CaretString

        /**
         * Value, extracted from formatted text according to mask format.
         */
        val extractedValue: String

        /**
         * Calculated absolute affinity value between the mask format and input text.
         */
        val affinity: Int

        /**
         * User input is complete.
         */
        val complete: Boolean

        init {
            this.formattedText = formattedText
            this.extractedValue = extractedValue
            this.affinity = affinity
            this.complete = complete
        }
    }

    companion object Factory {
        val cache: MutableMap<String, Mask> = HashMap()

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
        fun getOrCreate(format: String): Mask {
            var cachedMask: Mask? = cache[format]
            if (null == cachedMask) {
                cachedMask = Mask(format)
                cache[format] = cachedMask
            }
            return cachedMask
        }
    }

    private val initialState: State

    /**
     * Constructor.
     *
     * @param format mask format.
     *
     * @returns Initialized ```Mask``` instance.
     *
     * @throws ```FormatError``` if format string is incorrect.
     */
    init {
        this.initialState = Compiler().compile(format)
    }

    /**
     * Apply mask to the user input string.
     *
     * @param toText user input string with current cursor position
     *
     * @returns Formatted text with extracted value an adjusted cursor position.
     */
    fun apply(text: CaretString, autocomplete: Boolean): Result {
        val iterator: CaretStringIterator = CaretStringIterator(text)

        var affinity:       Int    = 0
        var extractedValue: String = ""
        var modifiedString: String = ""
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
            val next: Next? = state.autocomplete()
            if (null == next) break
            state = next.state
            modifiedString += if (null != next.insert) next.insert else ""
            extractedValue += if (null != next.value) next.value else ""
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
            state is EOLState
        )
    }

    /**
     * Generate placeholder.
     *
     * @return Placeholder string.
     */
    fun placeholder(): String {
        return this.appendPlaceholder(this.initialState, "")
    }

    /**
     * Minimal length of the text inside the field to fill all mandatory characters in the mask.
     *
     * @return Minimal satisfying count of characters inside the text field.
     */
    fun acceptableTextLength(): Int {
        var state: State? = this.initialState
        var length: Int = 0

        while (null != state && !(state is EOLState)) {
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
        var length: Int = 0

        while (null != state && !(state is EOLState)) {
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
        var length: Int = 0

        while (null != state && !(state is EOLState)) {
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
        var length: Int = 0

        while (null != state && !(state is EOLState)) {
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
            when (state.type) {
                OptionalValueState.StateType.AlphaNumeric -> {
                    return this.appendPlaceholder(state.child, placeholder + "-")
                }

                OptionalValueState.StateType.Literal -> {
                    return this.appendPlaceholder(state.child, placeholder + "a")
                }

                OptionalValueState.StateType.Numeric -> {
                    return this.appendPlaceholder(state.child, placeholder + "0")
                }
            }
        }

        if (state is ValueState) {
            when (state.type) {
                ValueState.StateType.AlphaNumeric -> {
                    return this.appendPlaceholder(state.child, placeholder + "-")
                }

                ValueState.StateType.Literal -> {
                    return this.appendPlaceholder(state.child, placeholder + "a")
                }

                ValueState.StateType.Numeric -> {
                    return this.appendPlaceholder(state.child, placeholder + "0")
                }
            }
        }

        return placeholder
    }
}
