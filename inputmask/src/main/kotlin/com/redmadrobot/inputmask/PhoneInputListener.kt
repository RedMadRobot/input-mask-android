package com.redmadrobot.inputmask

import android.text.TextWatcher
import android.widget.EditText
import com.redmadrobot.inputmask.helper.AffinityCalculationStrategy
import com.redmadrobot.inputmask.helper.Mask
import com.redmadrobot.inputmask.model.CaretString
import com.redmadrobot.inputmask.model.Country
import com.redmadrobot.inputmask.model.Notation

/**
 * ### PhoneInputListener
 *
 * A ```MaskedTextInputListener``` subclass for guessing a country based on the entered digit sequence
 *
 * Computed country dictates the phone formatting
 */
open class PhoneInputListener(
    primaryFormat: String,
    affineFormats: List<String> = emptyList(),
    customNotations: List<Notation> = emptyList(),
    affinityCalculationStrategy: AffinityCalculationStrategy = AffinityCalculationStrategy.WHOLE_STRING,
    autocomplete: Boolean = true,
    autoskip: Boolean = false,
    field: EditText,
    listener: TextWatcher? = null,
    valueListener: ValueListener? = null,
    rightToLeft: Boolean = false
): MaskedTextChangedListener(
    primaryFormat,
    affineFormats,
    customNotations,
    affinityCalculationStrategy,
    autocomplete,
    autoskip,
    field,
    listener,
    valueListener,
    rightToLeft
) {
    /**
     * A detected ```Country``` based on the entered digits
     */
    var computedCountry: Country? = null

    /**
     * A list of possible ```Country``` candidates based on the entered digits
     */
    var computedCountries: List<Country> = listOf()

    /**
     * Allowed ```Country``` list. Pre-filters the ```Country::all``` dictionary.
     *
     * May contain country names, native country names, ISO-3166 codes, country emojis, or their mix.
     *
     * E.g.
     * ```
     * listener.enableCountries = listOf(
     *   "Greece",
     *   "BE",
     *   "🇪🇸"
     * )
     * ```
     */
    var enableCountries: List<String>? = null

    /**
     * Blocked ```Country``` list. Pre-filters the ```Country::all``` dictionary.
     *
     * May contain country names, native country names, ISO-3166 codes, country emojis, or their mix.
     *
     * E.g.
     * ```
     * listener.disableCountries = listOf(
     *   "Greece",
     *   "BE",
     *   "🇪🇸"
     * )
     * ```
     */
    var disableCountries: List<String>? = null

    /**
    A custom ```Country``` list to be used instead of ```Country::all``` dictionary.
     */
    var customCountries: List<Country>? = null

    override fun pickMask(text: CaretString): Mask {
        computedCountries = Country.findCountries(customCountries, enableCountries, disableCountries, text.string)
        computedCountry = if (computedCountries.count() == 1) computedCountries.first() else null

        val country = computedCountry
        return if (country == null) {
            Mask("+[000] [000] [000] [00] [00]")
        } else {
            primaryFormat = country.primaryFormat
            affineFormats = country.affineFormats

            super.pickMask(text)
        }
    }
}
