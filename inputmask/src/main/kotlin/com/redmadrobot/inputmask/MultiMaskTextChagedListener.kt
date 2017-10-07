package com.redmadrobot.inputmask

import android.text.TextWatcher
import android.widget.EditText
import com.redmadrobot.inputmask.helper.Mask

/**
 * @author aleien on 14.09.17.
 */
class MultiMaskedTextChangedListener(input: EditText, listener: TextWatcher?, formats: Map<Int, String>) : MaskedTextChangedListener("+7 ([000]) [000]-[00]-[00]",
        true, input,
        listener, null) {

    private val maskPlusANY: Mask = Mask.getOrCreate("+[000000000000000]")
    private val maskANY = Mask.getOrCreate("[000000000000000]")
    private val masks: Map<Int, Mask> = formats.mapValues { entry -> Mask.getOrCreate(entry.value) }. plus('+'.toInt() to maskPlusANY)

    private fun CharSequence.firstDigitsAre(i: Int): Boolean {
        if (this.isBlank()) return false
        val phone = this.filter { it.isDigit() }.toString()
        if (phone.isBlank()) return false
        return phone.startsWith(i.toString())
    }


    override fun onTextChanged(text: CharSequence, cursorPosition: Int, before: Int, count: Int) {
        mask = if (text.startsWith('+')) maskPlusANY else maskANY
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