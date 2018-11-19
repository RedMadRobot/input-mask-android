package com.redmadrobot.inputmask.helper

/**
 * Created by taflanidi on 10.11.16.
 */
abstract class MaskTest {

    fun mask(): Mask {
        return Mask(format(), emptyList())
    }

    abstract fun format(): String

}
