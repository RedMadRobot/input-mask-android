package com.redmadrobot.inputmask

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString
import java.util.*

/**
 * ### PolyMaskTextChangedListener
 *
 * TextWatcher implementation, which applies masking to the user input, picking the most suitable mask for the text.
 *
 * Might be used as a decorator, which forwards TextWatcher calls to its own listener.
 */
class PolyMaskTextChangedListener(
    format: String,
    affineFormats: List<String>,
    autocomplete: Boolean,
    field: EditText,
    listener: TextWatcher?,
    valueListener: MaskedTextChangedListener.ValueListener?
) : MaskedTextChangedListener(format, autocomplete, field, listener, valueListener) {

    var affineFormats: List<String>

    init {
        this.affineFormats = affineFormats
    }

    override fun setText(text: String) {
        val result: Mask.Result =
            this.pickMask(
                text,
                text.length, this.autocomplete).apply(
                CaretString(
                    text,
                    text.length
                ),
                this.autocomplete
            )
        this.field.get().setText(result.formattedText.string)
        this.field.get().setSelection(result.formattedText.caretPosition)
        this.valueListener?.onTextChanged(result.extractedValue.length >= this.acceptableValueLength(), result.extractedValue)
    }

    override fun onTextChanged(text: CharSequence, cursorPosition: Int, before: Int, count: Int) {
        val isDeletion: Boolean = before > 0
        val result: Mask.Result =
            this.pickMask(
                text.toString(),
                if (isDeletion) cursorPosition else cursorPosition + count,
                this.autocomplete && !isDeletion
            ).apply(
                CaretString(
                    text.toString(),
                    if (isDeletion) cursorPosition else cursorPosition + count
                ),
                this.autocomplete && !isDeletion
            )
        this.afterText = result.formattedText.string
        this.caretPosition = if (isDeletion) cursorPosition else result.formattedText.caretPosition
        this.valueListener?.onTextChanged(result.extractedValue.length >= this.acceptableValueLength(), result.extractedValue)
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (this.autocomplete && hasFocus) {
            val text: String
            if (this.field.get().text.isEmpty()) {
                text = ""
            } else {
                text = this.field.get().text.toString()
            }

            val result: Mask.Result =
                this.pickMask(
                    text,
                    text.length,
                    this.autocomplete
                ).apply(
                    CaretString(
                        text,
                        text.length
                    ),
                    this.autocomplete
                )
            this.field.get().setText(result.formattedText.string)
            this.field.get().setSelection(result.formattedText.caretPosition)
            this.valueListener?.onTextChanged(result.extractedValue.length >= this.acceptableValueLength(), result.extractedValue)
        }
    }

    private fun pickMask(
        text: String,
        caretPosition: Int,
        autocomplete: Boolean
    ): Mask {
        val primaryAffinity: Int = this.calculateAffinity(
            this.mask,
            text,
            caretPosition,
            autocomplete
        )

        var masks: MutableList<Pair<Mask, Int>> = ArrayList()
        for (format in this.affineFormats) {
            val mask: Mask = Mask.getOrCreate(format)

            val affinity: Int = this.calculateAffinity(
                mask,
                text,
                caretPosition,
                autocomplete
            )

            masks.add(Pair(mask, affinity))
        }

        masks.sortBy { item ->
            item.second
        }

        var insertIndex: Int = -1

        for ((index, maskAffinity) in masks.withIndex()) {
            if (primaryAffinity >= maskAffinity.second) {
                insertIndex = index
                break
            }
        }

        if (insertIndex >= 0) {
            masks.add(insertIndex, Pair(this.mask, primaryAffinity))
        } else {
            masks.add(Pair(this.mask, primaryAffinity))
        }

        return masks.first().first
    }

    private fun calculateAffinity(
        mask: Mask,
        text: String,
        caretPosition: Int,
        autocomplete: Boolean
    ): Int {
        return mask.apply(
            CaretString(
                text,
                caretPosition
            ),
            autocomplete
        ).affinity
    }

}
