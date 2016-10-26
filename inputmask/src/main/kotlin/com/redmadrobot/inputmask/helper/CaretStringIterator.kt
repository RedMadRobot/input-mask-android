package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.CaretString

/**
 * ### CaretStringIterator
 *
 * Iterates over CaretString.string characters. Each ```next()``` call returns current character and
 * adjusts iterator position.
 *
 * ```CaretStringIterator``` is used by the ```Mask``` instance to iterate over the string that
 * should be formatted.
 *
 * @author taflanidi
 */
class CaretStringIterator(caretString: CaretString) {

    private val caretString: CaretString
    private var currentIndex: Int

    /**
     * Constructor.
     *
     * @param caretString ```CaretString``` object, over which the iterator is going to iterate.
     *
     * @returns Initialized ```CaretStringIterator``` pointing at the beginning of provided
     * ```CaretString.string```
     */
    init {
        this.caretString = caretString
        this.currentIndex = 0
    }

    /**
     * Inspect, whether ```CaretStringIterator``` has reached ```CaretString.caretPosition``` or
     * not.
     *
     * Each ```CaretString``` object contains cursor position for its ```CaretString.string```.
     *
     * For the ```Mask``` instance it is important to know, whether it should adjust the cursor
     * position or not when inserting new symbols into the formatted line.
     *
     * **Example**
     * Let the ```CaretString``` instance contains two symbols, with the caret at the end of the
     * line.
     *
     * ```
     * string:    ab
     * caret:      ^
     * ```
     *
     * In this case ```CaretStringIterator.beforeCaret()``` will always return ```true``` until
     * there's no more characters left in the line to iterate over.
     *
     * **Example 2**
     *
     * Let the ```CaretString``` instance contains two symbols, with the caret at the beginning of
     * the line.
     *
     * ```
     * string:    ab
     * caret:     ^
     * ```
     *
     * In this case ```CaretStringIterator.beforeCaret()``` will only return ```true``` for the
     * first iteration. After the
     *
     * ```next()``` method is fired, ```beforeCaret()``` will return false.
     *
     * @returns ```True```, if current iterator position is less than or equal to
     * ```CaretString.caretPosition```
     */
    fun beforeCaret(): Boolean {
        return this.currentIndex <= this.caretString.caretPosition
            || (0 == this.currentIndex && 0 == this.caretString.caretPosition)
    }

    /**
     * Iterate over the ```CaretString.string```
     * @postcondition: Iterator position is moved to the next symbol.
     * @returns Current symbol. If the iterator reached the end of the line, returns ```nil```.
     */
    fun next(): Char? {
        if (this.currentIndex >= this.caretString.string.length) {
            return null
        }

        val char: Char = this.caretString.string.toCharArray()[this.currentIndex]
        this.currentIndex += 1
        return char
    }

}