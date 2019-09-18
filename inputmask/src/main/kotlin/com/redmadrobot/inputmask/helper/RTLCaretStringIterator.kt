package com.redmadrobot.inputmask.helper

import com.redmadrobot.inputmask.model.CaretString

class RTLCaretStringIterator(caretString: CaretString) : CaretStringIterator(caretString) {
    override fun insertionAffectsCaret(): Boolean {
        return this.currentIndex <= this.caretString.caretPosition
    }
}