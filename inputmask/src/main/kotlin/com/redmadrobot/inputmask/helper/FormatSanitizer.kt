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
 * without nested brackets, like ```[[000]99]```. Square bracket ```[]``` groups may contain mixed
 * types of symbols ("0" and "9" with "A" and "a" or "_" and "-"), which sanitizer will divide into
 * separate groups. Such that, ```[0000Aa]``` group will be divided in two groups: ```[0000]```
 * and ```[Aa]```.
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
     * without nested brackets, like ```[[000]99]```. Square bracket ```[]``` groups may contain mixed
     * types of symbols ("0" and "9" with "A" and "a" or "_" and "-"), which sanitizer will divide into
     * separate groups. Such that, ```[0000Aa]``` group will be divided in two groups: ```[0000]```
     * and ```[Aa]```.
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

        val blocks: List<String> =
                this.divideBlocksWithMixedCharacters(this.getFormatBlocks(formatString))

        return this.sortFormatBlocks(blocks).joinToString("")
    }

    private fun getFormatBlocks(formatString: String): List<String> {
        val blocks: MutableList<String> = ArrayList()
        var currentBlock: String = ""

        formatString.toCharArray().forEach {
            when (it) {
                '[','{' -> {
                    if (currentBlock.isNotEmpty()) {
                        blocks.add(currentBlock)
                    }
                    currentBlock = ""
                }
                ']','}' -> {
                    blocks.add(currentBlock)
                    currentBlock = ""
                }
            }
            currentBlock += it
        }

        if (!currentBlock.isEmpty()) {
            blocks.add(currentBlock)
        }

        return blocks
    }

    private fun divideBlocksWithMixedCharacters(blocks: List<String>): List<String> {
        val resultingBlocks: MutableList<String> = ArrayList()

        blocks.forEach {
            when (it.first()) {
                '[' -> {
                    var blockBuffer = ""
                    for (blockCharacter in it) {
                        if (blockCharacter == '[') {
                            blockBuffer += blockCharacter
                            continue
                        }

                        if (blockCharacter == ']') {
                            blockBuffer += blockCharacter
                            resultingBlocks.add(blockBuffer)
                            break
                        }

                        if (blockCharacter == '0' || blockCharacter == '9') {
                            if (blockBuffer.contains("A")
                                    || blockBuffer.contains("a")
                                    || blockBuffer.contains("-")
                                    || blockBuffer.contains("_")) {
                                blockBuffer += "]"
                                resultingBlocks.add(blockBuffer)
                                blockBuffer = "[" + blockCharacter
                                continue
                            }
                        }

                        if (blockCharacter == 'A' || blockCharacter == 'a') {
                            if (blockBuffer.contains("0")
                                    || blockBuffer.contains("9")
                                    || blockBuffer.contains("-")
                                    || blockBuffer.contains("_")) {
                                blockBuffer += "]"
                                resultingBlocks.add(blockBuffer)
                                blockBuffer = "[" + blockCharacter
                                continue
                            }
                        }

                        if (blockCharacter == '-' || blockCharacter == '_') {
                            if (blockBuffer.contains("0")
                                    || blockBuffer.contains("9")
                                    || blockBuffer.contains("A")
                                    || blockBuffer.contains("a")) {
                                blockBuffer += "]"
                                resultingBlocks.add(blockBuffer)
                                blockBuffer = "[" + blockCharacter
                                continue
                            }
                        }

                        blockBuffer += blockCharacter
                    }
                }
                else -> resultingBlocks.add(it)
            }
        }

        return resultingBlocks
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

    private fun checkOpenBraces(string: String) {
        var squareBraceOpen: Boolean = false
        var curlyBraceOpen: Boolean = false

        for (char in string.toCharArray()) {
            when (char) {
                '[' -> {
                    if (squareBraceOpen) {
                        throw Compiler.FormatError()
                    }
                    squareBraceOpen = true
                }
                ']' -> squareBraceOpen = false
                '{' -> {
                    if (curlyBraceOpen) {
                        throw Compiler.FormatError()
                    }
                    curlyBraceOpen = true
                }
                '}' -> curlyBraceOpen = false
            }
        }
    }
}