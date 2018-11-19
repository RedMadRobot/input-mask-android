package com.redmadrobot.inputmask

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString
import com.redmadrobot.inputmask.model.Notation
import java.lang.ref.WeakReference
import java.util.ArrayList

/**
 * TextWatcher implementation.
 *
 * TextWatcher implementation, which applies masking to the user input, picking the most suitable mask for the text.
 *
 * Might be used as a decorator, which forwards TextWatcher calls to its own listener.
 */
open class MaskedTextChangedListener(
    var primaryFormat: String,
    var affineFormats: List<String> = emptyList(),
    var customNotations: List<Notation> = emptyList(),
    var affinityCalculationStrategy: AffinityCalculationStrategy = AffinityCalculationStrategy.WHOLE_STRING,
    var autocomplete: Boolean = true,
    field: EditText,
    var listener: TextWatcher? = null,
    var valueListener: ValueListener? = null
) : TextWatcher, View.OnFocusChangeListener {

    interface ValueListener {
        fun onTextChanged(maskFilled: Boolean, extractedValue: String)
    }

    private val primaryMask: Mask
        get() = Mask.getOrCreate(primaryFormat, customNotations)

    private var afterText: String = ""
    private var caretPosition: Int = 0

    private val field: WeakReference<EditText> = WeakReference(field)

    /**
     * Convenience constructor.
     */
    constructor(format: String, field: EditText) :
        this(format, field, null)

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
        this(format, emptyList(), emptyList(), AffinityCalculationStrategy.WHOLE_STRING, autocomplete, field, listener, valueListener)

    /**
     * Convenience constructor.
     */
    constructor(primaryFormat: String, affineFormats: List<String>, field: EditText) :
        this(primaryFormat, affineFormats, field, null)

    /**
     * Convenience constructor.
     */
    constructor(primaryFormat: String, affineFormats: List<String>, field: EditText, valueListener: ValueListener?) :
        this(primaryFormat, affineFormats, field, null, valueListener)

    /**
     * Convenience constructor.
     */
    constructor(primaryFormat: String, affineFormats: List<String>, field: EditText, listener: TextWatcher?, valueListener: ValueListener?) :
        this(primaryFormat, affineFormats, true, field, listener, valueListener)

    /**
     * Convenience constructor.
     */
    constructor(primaryFormat: String, affineFormats: List<String>, autocomplete: Boolean, field: EditText, listener: TextWatcher?, valueListener: ValueListener?) :
        this(primaryFormat, affineFormats, AffinityCalculationStrategy.WHOLE_STRING, autocomplete, field, listener, valueListener)

    /**
     * Convenience constructor.
     */
    constructor(primaryFormat: String, affineFormats: List<String>, affinityCalculationStrategy: AffinityCalculationStrategy, autocomplete: Boolean, field: EditText, listener: TextWatcher?, valueListener: ValueListener?) :
        this(primaryFormat, affineFormats, emptyList(), affinityCalculationStrategy, autocomplete, field, listener, valueListener)

    /**
     * Set text and apply formatting.
     * @param text - text; might be plain, might already have some formatting.
     */
    open fun setText(text: String): Mask.Result? {
        return this.field.get()?.let {
            val result = setText(text, it)
            this.valueListener?.onTextChanged(result.complete, result.extractedValue)
            return result
        }
    }

    /**
     * Set text and apply formatting.
     * @param text - text; might be plain, might already have some formatting;
     * @param field - a field where to put formatted text.
     */
    open fun setText(text: String, field: EditText): Mask.Result {
        val result: Mask.Result =
            this.pickMask(text, text.length, this.autocomplete).apply(
                CaretString(text, text.length),
                this.autocomplete
            )
        field.setText(result.formattedText.string)
        field.setSelection(result.formattedText.caretPosition)
        return result
    }

    /**
     * Generate placeholder.
     *
     * @return Placeholder string.
     */
    fun placeholder(): String {
        return this.primaryMask.placeholder()
    }

    /**
     * Minimal length of the text inside the field to fill all mandatory characters in the mask.
     *
     * @return Minimal satisfying count of characters inside the text field.
     */
    fun acceptableTextLength(): Int {
        return this.primaryMask.acceptableTextLength()
    }

    /**
     *  Maximal length of the text inside the field.
     *
     *  @return Total available count of mandatory and optional characters inside the text field.
     */
    fun totalTextLength(): Int {
        return this.primaryMask.totalTextLength()
    }

    /**
     * Minimal length of the extracted value with all mandatory characters filled.\
     *
     * @return Minimal satisfying count of characters in extracted value.
     */
    fun acceptableValueLength(): Int {
        return this.primaryMask.acceptableValueLength()
    }

    /**
     * Maximal length of the extracted value.
     *
     * @return Total available count of mandatory and optional characters for extracted value.
     */
    fun totalValueLength(): Int {
        return this.primaryMask.totalValueLength()
    }

    override fun afterTextChanged(edit: Editable?) {
        this.field.get()?.removeTextChangedListener(this)
        edit?.replace(0, edit.length, this.afterText)
        this.field.get()?.setSelection(this.caretPosition)
        this.field.get()?.addTextChangedListener(this)
        this.listener?.afterTextChanged(edit)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        this.listener?.beforeTextChanged(p0, p1, p2, p3)
    }

    override fun onTextChanged(text: CharSequence, cursorPosition: Int, before: Int, count: Int) {
        val isDeletion: Boolean = before > 0 && count == 0
        val caretPosition = if (isDeletion) cursorPosition else cursorPosition + count
        val result: Mask.Result =
            this.pickMask(text.toString(), caretPosition, this.autocomplete && !isDeletion).apply(
                CaretString(text.toString(), caretPosition),
                this.autocomplete && !isDeletion
            )
        this.afterText = result.formattedText.string
        this.caretPosition = if (isDeletion) cursorPosition else result.formattedText.caretPosition
        this.valueListener?.onTextChanged(result.complete, result.extractedValue)
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (this.autocomplete && hasFocus) {
            val text: String = if (this.field.get()?.text!!.isEmpty()) {
                ""
            } else {
                this.field.get()?.text.toString()
            }

            val result: Mask.Result =
                this.pickMask(text, text.length, this.autocomplete).apply(
                    CaretString(text, text.length),
                    this.autocomplete
                )
            this.field.get()?.setText(result.formattedText.string)
            this.field.get()?.setSelection(result.formattedText.caretPosition)
            this.valueListener?.onTextChanged(result.complete, result.extractedValue)
        }
    }

    private fun pickMask(
        text: String,
        caretPosition: Int,
        autocomplete: Boolean
    ): Mask {
        if (this.affineFormats.isEmpty()) return this.primaryMask

        data class MaskAffinity(val mask: Mask, val affinity: Int)
        val primaryAffinity: Int = this.calculateAffinity(this.primaryMask, text, caretPosition, autocomplete)

        val masksAndAffinities: MutableList<MaskAffinity> = ArrayList()
        for (format in this.affineFormats) {
            val mask: Mask = Mask.getOrCreate(format, this.customNotations)
            val affinity: Int = this.calculateAffinity(mask, text, caretPosition, autocomplete)
            masksAndAffinities.add(MaskAffinity(mask, affinity))
        }

        masksAndAffinities.sortByDescending { it.affinity }

        var insertIndex: Int = -1

        for ((index, maskAffinity) in masksAndAffinities.withIndex()) {
            if (primaryAffinity >= maskAffinity.affinity) {
                insertIndex = index
                break
            }
        }

        if (insertIndex >= 0) {
            masksAndAffinities.add(insertIndex, MaskAffinity(this.primaryMask, primaryAffinity))
        } else {
            masksAndAffinities.add(MaskAffinity(this.primaryMask, primaryAffinity))
        }

        return masksAndAffinities.first().mask
    }

    private fun calculateAffinity(
        mask: Mask,
        text: String,
        caretPosition: Int,
        autocomplete: Boolean
    ): Int {
        return this.affinityCalculationStrategy.calculateAffinityOfMask(
            mask,
            CaretString(text, caretPosition),
            autocomplete
        )
    }

    companion object {
        /**
         * Create a `MaskedTextChangedListener` instance and assign it as a field's
         * `TextWatcher` and `onFocusChangeListener`.
         */
        fun installOn(
            editText: EditText,
            primaryFormat: String,
            valueListener: ValueListener? = null
        ): MaskedTextChangedListener = installOn(
            editText,
            primaryFormat,
            emptyList(),
            AffinityCalculationStrategy.WHOLE_STRING,
            valueListener
        )

        /**
         * Create a `MaskedTextChangedListener` instance and assign it as a field's
         * `TextWatcher` and `onFocusChangeListener`.
         */
        fun installOn(
            editText: EditText,
            primaryFormat: String,
            affineFormats: List<String> = emptyList(),
            affinityCalculationStrategy: AffinityCalculationStrategy = AffinityCalculationStrategy.WHOLE_STRING,
            valueListener: ValueListener? = null
        ): MaskedTextChangedListener = installOn(
            editText,
            primaryFormat,
            affineFormats,
            emptyList(),
            affinityCalculationStrategy,
            true,
            null,
            valueListener
        )

        /**
         * Create a `MaskedTextChangedListener` instance and assign it as a field's
         * `TextWatcher` and `onFocusChangeListener`.
         */
        fun installOn(
            editText: EditText,
            primaryFormat: String,
            affineFormats: List<String> = emptyList(),
            customNotations: List<Notation> = emptyList(),
            affinityCalculationStrategy: AffinityCalculationStrategy = AffinityCalculationStrategy.WHOLE_STRING,
            autocomplete: Boolean = true,
            listener: TextWatcher? = null,
            valueListener: ValueListener? = null
        ): MaskedTextChangedListener {
            val maskedListener = MaskedTextChangedListener(
                primaryFormat,
                affineFormats,
                customNotations,
                affinityCalculationStrategy,
                autocomplete,
                editText,
                listener,
                valueListener
            )
            editText.addTextChangedListener(maskedListener)
            editText.onFocusChangeListener = maskedListener
            return maskedListener
        }
    }

}
