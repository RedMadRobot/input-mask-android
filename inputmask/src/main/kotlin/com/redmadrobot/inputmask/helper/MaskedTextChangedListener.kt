package com.redmadrobot.inputmask.helper

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.redmadrobot.inputmask.model.CaretString
import com.redmadrobot.inputmask.model.State
import com.redmadrobot.inputmask.model.state.*
import java.lang.ref.WeakReference

/**
 * TextWatcher implementation.
 *
 * Created by taflanidi on 30.08.16.
 */
class MaskedTextChangedListener(
        format: String,
        autocomplete: Boolean,
        field: EditText,
        listener: TextWatcher?,
        valueListener: ValueListener?
) : TextWatcher, View.OnFocusChangeListener {

    interface ValueListener {
        fun onExtracted(value: String)
        fun onMandatoryCharactersFilled(complete: Boolean)
    }

    var listener: TextWatcher?
    var valueListener: ValueListener?

    private val mask: Mask
    private val autocomplete: Boolean

    private var afterText: String = ""
    private var caretPosition: Int = 0

    private val field: WeakReference<EditText>

    init {
        this.mask = Mask(format)
        this.autocomplete = autocomplete
        this.listener = listener
        this.valueListener = valueListener
        this.field = WeakReference(field)
    }

    /**
     * Set text and apply formatting.
     * @param text - text; might be plain, might already have some formatting.
     */
    fun setText(text: String) {
        val result: Mask.Result =
                this.mask.apply(
                        CaretString(
                                text,
                                text.length
                        ),
                        this.autocomplete
                )
        this.field.get().setText(result.formattedText.string)
        this.field.get().setSelection(result.formattedText.caretPosition)
        this.valueListener?.onExtracted(result.extractedValue)
        this.valueListener?.onMandatoryCharactersFilled(result.extractedValue.length >= this.acceptableValueLength())
    }

    /**
     * Generate placeholder.
     *
     * @return Placeholder string.
     */
    fun placeholder(): String {
        return this.mask.placeholder()
    }

    /**
     * Minimal length of the text inside the field to fill all mandatory characters in the mask.
     *
     * @return Minimal satisfying count of characters inside the text field.
     */
    fun acceptableTextLength(): Int {
        return this.mask.acceptableTextLength()
    }

    /**
     *  Maximal length of the text inside the field.
     *
     *  @return Total available count of mandatory and optional characters inside the text field.
     */
    fun totalTextLength(): Int {
        return this.mask.totalTextLength()
    }

    /**
     * Minimal length of the extracted value with all mandatory characters filled.\
     *
     * @return Minimal satisfying count of characters in extracted value.
     */
    fun acceptableValueLength(): Int {
        return this.mask.acceptableValueLength()
    }

    /**
     * Maximal length of the extracted value.
     *
     * @return Total available count of mandatory and optional characters for extracted value.
     */
    fun totalValueLength(): Int {
        return this.mask.totalValueLength()
    }

    override fun afterTextChanged(edit: Editable?) {
        this.field.get().removeTextChangedListener(this)
        edit?.clear()
        edit?.append(this.afterText)
        this.field.get().setSelection(this.caretPosition)
        this.field.get().addTextChangedListener(this)
        this.listener?.afterTextChanged(edit)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        this.listener?.beforeTextChanged(p0, p1, p2, p3)
    }

    override fun onTextChanged(text: CharSequence, cursorPosition: Int, before: Int, count: Int) {
        val isDeletion: Boolean = before > 0
        val result: Mask.Result =
                this.mask.apply(
                        CaretString(
                                text.toString(),
                                if (isDeletion) cursorPosition else cursorPosition + count
                        ),
                        this.autocomplete && !isDeletion
                )
        this.afterText = result.formattedText.string
        this.caretPosition = result.formattedText.caretPosition
        this.valueListener?.onExtracted(result.extractedValue)
        this.valueListener?.onMandatoryCharactersFilled(result.extractedValue.length >= this.acceptableValueLength())
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
                    this.mask.apply(
                            CaretString(
                                    text,
                                    text.length
                            ),
                            this.autocomplete
                    )
            this.field.get().setText(result.formattedText.string)
            this.field.get().setSelection(result.formattedText.caretPosition)
            this.valueListener?.onExtracted(result.extractedValue)
            this.valueListener?.onMandatoryCharactersFilled(result.extractedValue.length >= this.acceptableValueLength())
        }
    }

}