package com.pullvert.collections

import org.apache.commons.lang3.SerializationUtils
import kotlin.test.*

class MultiIntIterableTest {

    @Test
    fun verifyMultiIntIterableSpliterator() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = MultiIntIterable(initialValue)
        val spliterator = singleIntIterable.spliterator()
        val values = mutableListOf<Int>()
        spliterator.forEachRemaining { value -> values.add(value) }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Ignore
    @Test
    fun verifyMultiIntIterableSerializable() {
        val initialValue = intArrayOf(1, 2)
        val original = MultiIntIterable(initialValue)
        val copy = SerializationUtils.clone(original)
        assertEquals(original, copy)
    }
}
