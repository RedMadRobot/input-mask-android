package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.State
import com.redmadrobot.inputmask.model.state.*

/**
 * ### Compiler
 *
 * Creates a sequence of states from the mask format string.
 * @see ```State``` class.
 *
 * @complexity ```O(formatString.characters.count)``` plus ```FormatSanitizer``` complexity.
 *
 * @requires Format string to contain only flat groups of symbols in ```[]``` and ```{}``` brackets
 * without nested brackets, like ```[[000]99]```. Also, ```[…]``` groups may contain only the
 * specified characters ("0", "9", "A", "a", "_" and "-"). Square bracket ```[]``` groups cannot
 * contain mixed types of symbols ("0" and "9" with "A" and "a" or "_" and "-").
 *
 * ```Compiler``` object is initialized and ```Compiler.compile(formatString:)``` is called during
 * the ```Mask``` instance initialization.
 *
 * ```Compiler``` uses ```FormatSanitizer``` to prepare ```formatString``` for the compilation.
 *
 * @author taflanidi
 */
class Compiler {

    /**
     * ### FormatError
     *
     * Compiler error exception type, thrown when ```formatString``` contains inappropriate
     * character sequences.
     *
     * ```FormatError``` is used by the ```Compiler``` and ```FormatSanitizer``` classes.
     */
    class FormatError : Exception()

/**
 * Compile ```formatString``` into the sequence of states.
 *
 * * "Free" characters from ```formatString``` are converted to ```FreeState```-s.
 * * Characters in square brackets are converted to ```ValueState```-s and ```OptionalValueState```-s.
 * * Characters in curly brackets are converted to ```FixedState```-s.
 * * End of the formatString line makes ```EOLState```.
 *
 * For instance,
 *
 * ```
 * [09]{.}[09]{.}19[00]
 * ```
 *
 * is converted to sequence:
 *
 * ```
 * 0. ValueState.Numeric          [0]
 * 1. OptionalValueState.Numeric  [9]
 * 2. FixedState                  {.}
 * 3. ValueState.Numeric          [0]
 * 4. OptionalValueState.Numeric  [9]
 * 5. FixedState                  {.}
 * 6. FreeState                    1
 * 7. FreeState                    9
 * 8. ValueState.Numeric          [0]
 * 9. ValueState.Numeric          [0]
 * ```
 *
 * @param formatString string with a mask format.
 *
 * @see ```State``` class.
 *
 * @complexity ```O(formatString.characters.count)``` plus ```FormatSanitizer``` complexity.
 *
 * @requires: Format string to contain only flat groups of symbols in ```[]``` and ```{}``` brackets
 * without nested brackets, like ```[[000]99]```. Also, ```[…]``` groups may contain only the
 * specified characters ("0", "9", "A", "a", "_" and "-").
 *
 * @returns Initialized ```State``` object with assigned ```State.child``` chain.
 *
 * @throws ```FormatError``` if ```formatString``` does not conform to the method requirements.
 */
    @Throws(FormatError::class)
    fun compile(formatString: String): State {
        val sanitizedString: String = FormatSanitizer().sanitize(formatString)

        return this.compile(
                sanitizedString,
                false,
                false
        )
    }

    private fun compile(formatString: String, valueable: Boolean, fixed: Boolean): State {
        if (formatString.isEmpty()) {
            return EOLState()
        }

        val char: Char = formatString.first()

        when (formatString.first()) {
            '[' -> return this.compile(
                    formatString.drop(1),
                    true,
                    false
                    )
            '{' -> return this.compile(
                    formatString.drop(1),
                    false,
                    true
                    )
            ']','}' -> return this.compile(
                    formatString.drop(1),
                    false,
                    false
                    )
        }

        if (valueable) {
            return when (formatString.first()) {
                '0' -> ValueState(
                        this.compile(
                            formatString.drop(1),
                            true,
                            false
                        ),
                        State.StateType.Numeric
                )
                'A' -> ValueState(
                        this.compile(
                            formatString.drop(1),
                            true,
                            false
                        ),
                        State.StateType.Literal
                )
                '_' -> ValueState(
                        this.compile(
                            formatString.drop(1),
                            true,
                            false
                        ),
                        State.StateType.AlphaNumeric
                )
                '9' -> OptionalValueState(
                        this.compile(
                            formatString.drop(1),
                            true,
                            false
                        ),
                        State.StateType.Numeric
                )
                'a' -> OptionalValueState(
                        this.compile(
                            formatString.drop(1),
                            true,
                            false
                        ),
                        State.StateType.Literal
                )
                '-' -> OptionalValueState(
                        this.compile(
                            formatString.drop(1),
                            true,
                            false
                        ),
                        State.StateType.AlphaNumeric
                )
                else -> throw FormatError()
            }

        }

        if (fixed) {
            return FixedState(
                    this.compile(
                            formatString.drop(1),
                            false,
                            true
                    ),
                    char
            )
        }

        return FreeState(
                this.compile(
                        formatString.drop(1),
                        false,
                        false
                ),
                char
        )
    }
}

