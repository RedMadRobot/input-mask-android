package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.CaretString

/**
 * ### AffinityCalculationStrategy
 *
 * Allows to opt for a different mask picking algorithm in text field listeners.
 */
enum class AffinityCalculationStrategy {
    /**
     * Default strategy.
     *
     * Uses ```Mask``` built-in mechanism to calculate total affinity between the text and the mask format.
     */
    WHOLE_STRING,

    /**
     * Finds the longest common prefix between the original text and the same text after applying the mask.
     */
    PREFIX;

    fun calculateAffinityOfMask(mask: Mask, text: CaretString, autocomplete: Boolean): Int {
        return when (this) {
            WHOLE_STRING -> mask.apply(
                CaretString(text.string, text.caretPosition),
                autocomplete
            ).affinity

            PREFIX -> mask.apply(
                CaretString(text.string, text.caretPosition),
                autocomplete
            ).formattedText.string.prefixIntersection(text.string).length
        }
    }

    private fun String.prefixIntersection(another: String): String {
        if (this.isEmpty() || another.isEmpty()) return ""

        var endIndex = 0
        while (endIndex < this.length && endIndex < another.length) {
            if (this[endIndex] == another[endIndex]) {
                endIndex += 1
            } else {
                return this.substring(0, endIndex)
            }
        }

        return this.substring(0, endIndex)
    }
}