package com.redmadrobot.inputmask.helper

import org.junit.Assert
import org.junit.Test
import java.util.ArrayList

/**
 * Created by taflanidi on 10.11.16.
 */
abstract class MaskTest {

    fun mask(): Mask {
        return Mask(format())
    }

    abstract fun format(): String

    @Test
    fun init_correctFormat_maskInitialized() {
        Assert.assertNotNull(this.mask())
    }

    @Test
    fun init_correctFormat_measureTime() {
        val startTime = System.nanoTime()

        var masks: MutableList<Mask> = ArrayList()
        for (i in 1..1000) {
            masks.add(Mask(this.format()))
        }
        val endTime = System.nanoTime()
        val duration = (endTime - startTime) / 1000000

        println("${javaClass.simpleName}, init took $duration milliseconds")
    }

    @Test
    fun getOrCreate_correctFormat_measureTime() {
        val startTime = System.nanoTime()

        var masks: MutableList<Mask> = ArrayList()
        for (i in 1..1000) {
            masks.add(Mask.getOrCreate(this.format()))
        }
        val endTime = System.nanoTime()
        val duration = (endTime - startTime) / 1000000

        println("${javaClass.simpleName}, getOrCreate took $duration milliseconds")
    }

}
