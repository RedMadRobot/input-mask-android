package com.redmadrobot.inputmask

import android.text.TextWatcher
import android.widget.EditText
import com.redmadrobot.inputmask.helper.Mask

/**
 *
 * @author aleien on 14.09.17.
 */

class MultiPhoneMaskedTextChangedListener @JvmOverloads constructor(formats: Map<Int, String>,
       input: EditText, listener: TextWatcher?, autocomplete: Boolean = true, valueListener: ValueListener? = null) :
        MaskedTextChangedListener(DEFAULT_PLUS, autocomplete, input, listener, valueListener) {

    private val maskPlusDefault: Mask = Mask.getOrCreate(DEFAULT_PLUS)
    private val maskDefault = Mask.getOrCreate(DEFAULT_DIGITS)

    private val masks: Map<Int, Mask> = formats.mapValues { entry -> Mask.getOrCreate(entry.value) }

    private fun CharSequence.firstDigitsAre(i: Int): Boolean {
        if (this.isBlank()) return false
        val phone = this.filter { it.isDigit() }.toString()
        if (phone.isBlank()) return false
        return phone.startsWith(i.toString())
    }


    override fun onTextChanged(text: CharSequence, cursorPosition: Int, before: Int, count: Int) {
        mask = if (text.startsWith(PLUS_CHAR)) maskPlusDefault else maskDefault

        // we shouldn't use masks.forEach here, because it'll crash on sdk < 19
        for ((key, value) in masks) {
            if (text.firstDigitsAre(key)) {
                mask = value
                break
            }
        }

        super.onTextChanged(text, cursorPosition, before, count)
    }
}

const val DEFAULT_PLUS = "+[000000000000000]"
const val DEFAULT_DIGITS = "[000000000000000]"
const val PLUS_CHAR = '+'