package com.pullvert.collections

import org.apache.commons.lang3.SerializationUtils
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InlineIntIterableTest {

    @Test
    fun verifyInlineIntIterableSpliterator() {
        val initialValue = intArrayOf(1, 2)
        val intIterable = InlineIntIterable(initialValue)
        val spliterator = intIterable.spliterator()
        val values = mutableListOf<Int>()
        spliterator.forEachRemaining { value -> values.add(value) }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyInlineIntIterableSerializable() {
        val initialValue = intArrayOf(1, 2)
        val original = InlineIntIterable(initialValue)
        val copy = SerializationUtils.clone(original)
        assertTrue(original.array.contentEquals(copy.array))
        //assertEquals(original, copy) // uncomment when this feature is available
    }
}
