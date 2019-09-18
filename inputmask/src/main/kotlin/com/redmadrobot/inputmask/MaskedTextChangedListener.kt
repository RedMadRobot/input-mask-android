package com.redmadrobot.inputmask

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.helper.RTLMask
import com.redmadrobot.inputmask.model.CaretString
import com.redmadrobot.inputmask.model.Notation
import java.lang.ref.WeakReference
import java.util.*

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
    var valueListener: ValueListener? = null,
    var rightToLeft: Boolean = false
) : TextWatcher, View.OnFocusChangeListener {

    interface ValueListener {
        fun onTextChanged(maskFilled: Boolean, extractedValue: String, formattedValue: String)
    }

    private val primaryMask: Mask
        get() = this.maskGetOrCreate(this.primaryFormat, this.customNotations)

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
    constructor(
        format: String, autocomplete: Boolean, field: EditText, listener: TextWatcher?,
        valueListener: ValueListener?
    ) :
            this(
                format, emptyList(), emptyList(), AffinityCalculationStrategy.WHOLE_STRING, autocomplete, field,
                listener, valueListener
            )

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
    constructor(
        primaryFormat: String, affineFormats: List<String>, field: EditText, listener: TextWatcher?,
        valueListener: ValueListener?
    ) :
            this(primaryFormat, affineFormats, true, field, listener, valueListener)

    /**
     * Convenience constructor.
     */
    constructor(
        primaryFormat: String, affineFormats: List<String>, autocomplete: Boolean, field: EditText,
        listener: TextWatcher?, valueListener: ValueListener?
    ) :
            this(
                primaryFormat, affineFormats, AffinityCalculationStrategy.WHOLE_STRING, autocomplete, field, listener,
                valueListener
            )

    /**
     * Convenience constructor.
     */
    constructor(
        primaryFormat: String, affineFormats: List<String>,
        affinityCalculationStrategy: AffinityCalculationStrategy, autocomplete: Boolean, field: EditText,
        listener: TextWatcher?, valueListener: ValueListener?
    ) :
            this(
                primaryFormat, affineFormats, emptyList(), affinityCalculationStrategy, autocomplete, field, listener,
                valueListener
            )

    /**
     * Set text and apply formatting.
     * @param text - text; might be plain, might already have some formatting.
     */
    open fun setText(text: String): Mask.Result? {
        return this.field.get()?.let {
            val result = setText(text, it)
            this.afterText = result.formattedText.string
            this.caretPosition = result.formattedText.caretPosition
            this.valueListener?.onTextChanged(result.complete, result.extractedValue, afterText)
            return result
        }
    }

    /**
     * Set text and apply formatting.
     * @param text - text; might be plain, might already have some formatting;
     * @param field - a field where to put formatted text.
     */
    open fun setText(text: String, field: EditText): Mask.Result {
        val text = CaretString(text, text.length, CaretString.CaretGravity.FORWARD)
        val result: Mask.Result = this.pickMask(text, this.autocomplete).apply(text, this.autocomplete)

        with(field) {
            setText(result.formattedText.string)
            setSelection(result.formattedText.caretPosition)
        }

        return result
    }

    /**
     * Generate placeholder.
     *
     * @return Placeholder string.
     */
    fun placeholder(): String = this.primaryMask.placeholder()

    /**
     * Minimal length of the text inside the field to fill all mandatory characters in the mask.
     *
     * @return Minimal satisfying count of characters inside the text field.
     */
    fun acceptableTextLength(): Int = this.primaryMask.acceptableTextLength()

    /**
     *  Maximal length of the text inside the field.
     *
     *  @return Total available count of mandatory and optional characters inside the text field.
     */
    fun totalTextLength(): Int = this.primaryMask.totalTextLength()

    /**
     * Minimal length of the extracted value with all mandatory characters filled.\
     *
     * @return Minimal satisfying count of characters in extracted value.
     */
    fun acceptableValueLength(): Int = this.primaryMask.acceptableValueLength()

    /**
     * Maximal length of the extracted value.
     *
     * @return Total available count of mandatory and optional characters for extracted value.
     */
    fun totalValueLength(): Int = this.primaryMask.totalValueLength()

    override fun afterTextChanged(edit: Editable?) {
        this.field.get()?.removeTextChangedListener(this)
        edit?.replace(0, edit.length, this.afterText)
        this.field.get()?.setSelection(this.caretPosition)
        this.field.get()?.addTextChangedListener(this)
        this.listener?.afterTextChanged(edit)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        this.listener?.beforeTextChanged(s, start, count, after)
    }

    override fun onTextChanged(text: CharSequence, cursorPosition: Int, before: Int, count: Int) {
        val isDeletion: Boolean = before > 0 && count == 0
        val caretPosition = if (isDeletion) cursorPosition else cursorPosition + count
        val caretGravity = if (isDeletion) CaretString.CaretGravity.BACKWARD else CaretString.CaretGravity.FORWARD
        val text = CaretString(text.toString(), caretPosition, caretGravity)
        val useAutocomplete = if (isDeletion) false else this.autocomplete

        val mask: Mask = this.pickMask(text, useAutocomplete)
        val result: Mask.Result = mask.apply(text, useAutocomplete)

        this.afterText = result.formattedText.string
        this.caretPosition = result.formattedText.caretPosition

        this.valueListener?.onTextChanged(result.complete, result.extractedValue, afterText)
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (this.autocomplete && hasFocus) {
            val text: String = if (this.field.get()?.text!!.isEmpty()) {
                ""
            } else {
                this.field.get()?.text.toString()
            }

            val textAndCaret = CaretString(text, text.length, CaretString.CaretGravity.FORWARD)

            val result: Mask.Result =
                this.pickMask(textAndCaret, this.autocomplete).apply(textAndCaret, this.autocomplete)

            this.afterText = result.formattedText.string
            this.caretPosition = result.formattedText.caretPosition
            this.field.get()?.setText(afterText)
            this.field.get()?.setSelection(result.formattedText.caretPosition)
            this.valueListener?.onTextChanged(result.complete, result.extractedValue, afterText)
        }
    }

    private fun pickMask(
        text: CaretString,
        autocomplete: Boolean
    ): Mask {
        if (this.affineFormats.isEmpty()) return this.primaryMask

        data class MaskAffinity(val mask: Mask, val affinity: Int)

        val primaryAffinity: Int = this.calculateAffinity(this.primaryMask, text, autocomplete)

        val masksAndAffinities: MutableList<MaskAffinity> = ArrayList()
        for (format in this.affineFormats) {
            val mask: Mask = this.maskGetOrCreate(format, this.customNotations)
            val affinity: Int = this.calculateAffinity(mask, text, autocomplete)
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

    private fun maskGetOrCreate(format: String, customNotations: List<Notation>): Mask =
        if (this.rightToLeft) {
            RTLMask.getOrCreate(format, customNotations)
        } else {
            Mask.getOrCreate(format, customNotations)
        }

    private fun calculateAffinity(
        mask: Mask,
        text: CaretString,
        autocomplete: Boolean
    ): Int {
        return this.affinityCalculationStrategy.calculateAffinityOfMask(
            mask,
            text,
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
