package com.redmadrobot.inputmask.helper

import java.util.*

/**
 * ### FormatSanitizer
 *
 * Sanitizes given ```formatString``` before it's compilation.
 *
 * @complexity ```O(2*floor(log(n)))```, and switches to ```O(n^2)``` for ```n < 20``` where
 * ```n = formatString.characters.count```
 *
 * @requires Format string to contain only flat groups of symbols in ```[]``` and ```{}``` brackets
 * without nested brackets, like ```[[000]99]```. Square bracket ```[]``` groups cannot contain
 * mixed types of symbols ("0" and "9" with "A" and "a" or "_" and "-").
 *
 * ```FormatSanitizer``` is used by ```Compiler``` before format string compilation.
 *
 * @author taflanidi
 */
class FormatSanitizer {

/**
 * Sanitize ```formatString``` before compilation.
 *
 * In order to do so, sanitizer splits the string into groups of regular symbols, symbols in square
 * brackets [] and symbols in curly brackets {}. Then, characters in square brackets are sorted in
 * a way that mandatory symbols go before optional symbols. For instance,
 *
 * ```
 * a ([0909]) b
 * ```
 *
 * mask format is rearranged to
 *
 * ```
 * a ([0099]) b
 * ```
 * @complexity ```O(2*floor(log(n)))```, and switches to ```O(n^2)``` for ```n < 20``` where
 * ```n = formatString.characters.count```
 *
 * @requires Format string to contain only flat groups of symbols in ```[]``` and ```{}``` brackets
 * without nested brackets, like ```[[000]99]```. Square bracket ```[]``` groups cannot contain
 * mixed types of symbols ("0" and "9" with "A" and "a" or "_" and "-").
 *
 * @param formatString: mask format string.
 *
 * @returns Sanitized format string.
 *
 * @throws ```FormatError``` if ```formatString``` does not conform to the method requirements.
 */
    @Throws(Compiler.FormatError::class)
    fun sanitize(formatString: String): String {
        this.checkOpenBraces(formatString)

        val blocks: List<String> = this.getFormatBlocks(formatString)
        this.checkFormatBlocks(blocks)

        return this.sortFormatBlocks(blocks).joinToString("")
    }

    private fun sortFormatBlocks(blocks: List<String>): List<String> {
        var sortedBlocks: MutableList<String> = ArrayList()

        for (block in blocks) {
            var sortedBlock: String
            if (block.startsWith("[")) {
                if (block.contains("0") || block.contains("9")) {
                    sortedBlock = "[" + block.replace("[", "").replace("]", "").toCharArray().sorted().joinToString("") + "]"
                } else if (block.contains("a") || block.contains("A")) {
                    sortedBlock = "[" + block.replace("[", "").replace("]", "").toCharArray().sorted().joinToString("") + "]"
                } else {
                    sortedBlock = "[" + block.replace("[", "").replace("]", "").replace("_", "A").replace("-", "a").toCharArray().sorted().joinToString("") + "]"
                    sortedBlock = sortedBlock.replace("A", "_").replace("a", "-")
                }
            } else {
                sortedBlock = block
            }

            sortedBlocks.add(sortedBlock)
        }

        return sortedBlocks
    }

    private fun checkFormatBlocks(blocks: List<String>) {
        for (block in blocks) {
            if (block.startsWith("[")) {
                if (block.contains("0") || block.contains("9")) {
                    if (block.contains("A") || block.contains("a")) throw Compiler.FormatError()
                    if (block.contains("-") || block.contains("_")) throw Compiler.FormatError()
                }

                if (block.contains("a") || block.contains("A")) {
                    if (block.contains("0") || block.contains("9")) throw Compiler.FormatError()
                    if (block.contains("-") || block.contains("_")) throw Compiler.FormatError()
                }

                if (block.contains("-") || block.contains("_")) {
                    if (block.contains("0") || block.contains("9")) throw Compiler.FormatError()
                    if (block.contains("A") || block.contains("a")) throw Compiler.FormatError()
                }
            }
        }
    }

    private fun getFormatBlocks(formatString: String): List<String> {
        var blocks: MutableList<String> = ArrayList()
        var currentBlock: String = ""

        for (char in formatString.toCharArray()) {
            if ('[' == char || '{' == char) {
                if (0 < currentBlock.length) {
                    blocks.add(currentBlock)
                }
                currentBlock = ""
            }

            currentBlock += char

            if (']' == char || '}' == char) {
                blocks.add(currentBlock)
                currentBlock = ""
            }
        }

        if (!currentBlock.isEmpty()) {
            blocks.add(currentBlock)
        }

        return blocks
    }

    private fun checkOpenBraces(string: String) {
        var squareBraceOpen: Boolean = false
        var curlyBraceOpen: Boolean = false

        for (char in string.toCharArray()) {
            if ('[' == char) {
                if (squareBraceOpen) {
                    throw Compiler.FormatError()
                }
                squareBraceOpen = true
            }

            if (']' == char) {
                squareBraceOpen = false
            }

            if ('{' == char) {
                if (curlyBraceOpen) {
                    throw Compiler.FormatError()
                }
                curlyBraceOpen = true
            }

            if ('}' == char) {
                curlyBraceOpen = false
            }
        }
    }

}