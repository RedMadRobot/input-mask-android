package com.redmadrobot.sample

import android.graphics.Paint
import android.graphics.Rect
import android.icu.util.Currency
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.MarginLayoutParams
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import com.redmadrobot.inputmask.MaskedTextChangedListener.ValueListener
import com.redmadrobot.inputmask.NumberInputListener
import com.redmadrobot.inputmask.PhoneInputListener
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy

/**
 * Home screen for the sample app.
 *
 * @author taflanidi
 */
class MainActivity : AppCompatActivity() {
    private var listener: PhoneInputListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCurrencySample()
        setupDateSample()
        setupPhoneSample()
    }

    private fun setupCurrencySample() {
        val editText = findViewById<EditText>(R.id.currency_edit_text)
        val checkBox = findViewById<CheckBox>(R.id.currency_check_box)
        val listener = NumberInputListener.installOn(
            editText,
            object : ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String,
                    tailPlaceholder: String
                ) {
                    logValueListener(maskFilled, extractedValue, formattedValue)
                    checkBox.isChecked = maskFilled
                }
            }
        )
        listener.formatter = listener.formatter.unit(Currency.getInstance("USD"))
        editText.hint = listener.placeholder()
    }

    private fun setupDateSample() {
        val editText = findViewById<EditText>(R.id.date_edit_text)
        val checkBox = findViewById<CheckBox>(R.id.date_check_box)
        val placeholder = findViewById<TextView>(R.id.date_placeholder_text_view)
        val listener = installOn(
            editText,
            "[90].[90].[0000]",
            ArrayList(),
            ArrayList(),
            AffinityCalculationStrategy.WHOLE_STRING,
            autocomplete = true,
            autoskip = true,
            listener = null,
            valueListener = object : ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String,
                    tailPlaceholder: String
                ) {
                    logValueListener(maskFilled, extractedValue, formattedValue)
                    checkBox.isChecked = maskFilled
                    placeholder.text = if (formattedValue.isEmpty()) "" else tailPlaceholder
                    val textBounds = Rect()
                    val paint: Paint = placeholder.paint
                    paint.getTextBounds(formattedValue, 0, formattedValue.length, textBounds)
                    val width = textBounds.width()
                    val placeholderParams = placeholder.layoutParams as MarginLayoutParams
                    placeholderParams.marginStart = width + 16 // 16 is an empirical value
                }
            }
        )
        editText.hint = listener.placeholder()
    }

    private fun setupPhoneSample() {
        val editText = findViewById<EditText>(R.id.phone_edit_text)
        val checkBox = findViewById<CheckBox>(R.id.phone_check_box)
        val countriesTextView = findViewById<TextView>(R.id.countries_text_view)
        listener = PhoneInputListener.installOn(
            editText,
            autocomplete = true,
            autoskip = false,
            listener = null,
            valueListener = object : ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String,
                    tailPlaceholder: String
                ) {
                    logValueListener(maskFilled, extractedValue, formattedValue)
                    checkBox.isChecked = maskFilled
                    val countriesList = StringBuilder()
                    this@MainActivity.listener?.computedCountries?.forEach { country ->
                        countriesList
                            .append(country.countryCode)
                            .append(" ")
                            .append(country.emoji)
                            .append(" ")
                            .append(country.name)
                            .append("\n")
                    }
                    countriesTextView.text = countriesList.toString()
                }
            }
        )
        editText.hint = listener?.placeholder()
    }

    private fun logValueListener(
        maskFilled: Boolean,
        extractedValue: String,
        formattedText: String
    ) {
        val className = MainActivity::class.java.simpleName
        Log.d(className, extractedValue)
        Log.d(className, maskFilled.toString())
        Log.d(className, formattedText)
    }
}
