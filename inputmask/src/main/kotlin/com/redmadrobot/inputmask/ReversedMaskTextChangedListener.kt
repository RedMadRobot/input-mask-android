package com.redmadrobot.inputmask

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString
import com.redmadrobot.inputmask.model.Notation


/**
 * TextWatcher implementation for text fields with right text alignment.
 */
class ReversedMaskTextChangedListener(
    format: String,
    customNotations: List<Notation> = emptyList(),
    autocomplete: Boolean = true,
    field: EditText,
    listener: TextWatcher? = null,
    valueListener: ValueListener? =null
) : MaskedTextChangedListener(
    format,
    customNotations,
    autocomplete,
    field,
    listener,
    valueListener
) {
    private val reversedMask: Mask = Mask.getOrCreate(format.reversedFormat(), customNotations)

    /**
     * Convenience constructor.
     */
    constructor(format: String, field: EditText, valueListener: ValueListener?) :
        this(format, field, null, valueListener)

    /**
     * Convenience constructor.
     */
    constructor(format: String, field: EditText, listener: TextWatcher?, valueListener: ValueListener?) :
        this(format, true, field, listener, valueListener)

    /**
     * Convenience constructor.
     */
    constructor(format: String, autocomplete: Boolean, field: EditText, listener: TextWatcher?, valueListener: ValueListener?) :
        this(format, emptyList(), autocomplete, field, listener, valueListener)

    override fun setText(text: String) {
        val result: Mask.Result =
            this.reversedMask.apply(
                CaretString(
                    text.reversed(),
                    text.length
                ),
                this.autocomplete
            )
        this.field.get()?.setText(result.formattedText.string.reversed())
        this.field.get()?.setSelection(result.formattedText.caretPosition)
        this.valueListener?.onTextChanged(result.complete, result.extractedValue.reversed())
    }

    override fun onTextChanged(text: CharSequence, cursorPosition: Int, before: Int, count: Int) {
        val isDeletion: Boolean = before > 0 && count == 0

        val oldText = text.toString()
        val oldCaretPosition =
            if (isDeletion)
                oldText.length - cursorPosition
            else
                oldText.length - (cursorPosition + count)

        val result: Mask.Result =
            this.reversedMask.apply(
                CaretString(
                    oldText.reversed(),
                    oldCaretPosition
                ),
                this.autocomplete && !isDeletion
            )
        val formattedText = result.formattedText.string

        this.afterText = formattedText.reversed()
        this.caretPosition = formattedText.length - result.formattedText.caretPosition

        this.valueListener?.onTextChanged(result.complete, result.extractedValue.reversed())
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (this.autocomplete && hasFocus) {
            val text: String = if (this.field.get()?.text!!.isEmpty()) {
                ""
            } else {
                this.field.get()?.text.toString()
            }

            val result: Mask.Result =
                this.reversedMask.apply(
                    CaretString(
                        text.reversed(),
                        text.length
                    ),
                    this.autocomplete
                )
            this.field.get()?.setText(result.formattedText.string.reversed())
            this.field.get()?.setSelection(result.formattedText.caretPosition)
            this.valueListener?.onTextChanged(result.complete, result.extractedValue)
        }
    }

    private fun String.reversedFormat() = this.reversed()
        .replace("[\\", "\\]")
        .replace("]\\", "\\[")
        .replace("{\\", "\\}")
        .replace("}\\", "\\{")
        .map { c: Char ->
            when (c) {
                '[' -> ']'
                ']' -> '['
                '{' -> '}'
                '}' -> '{'
                else -> c
            }
        }.joinToString("")

}