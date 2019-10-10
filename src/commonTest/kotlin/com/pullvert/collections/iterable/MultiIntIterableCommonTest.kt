package com.pullvert.collections

import kotlin.test.*

class MultiIntIterableCommonTest {

    @Test
    fun verifyMultiIntIterable() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = MultiIntIterable(initialValue)
        val values = mutableListOf<Int>()
        for (value in singleIntIterable) {
            values.add(value)
        }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyMultiIntIterableForEach() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = MultiIntIterable(initialValue)
        val values = mutableListOf<Int>()
        singleIntIterable.forEach { value -> values.add(value) }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyMultiIntIterableInlineForEach() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = MultiIntIterable(initialValue)
        val values = mutableListOf<Int>()
        singleIntIterable.inlineForEach { value -> values.add(value) }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyMultiIntIterableMap() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = MultiIntIterable(initialValue)
        val values = singleIntIterable.map { value -> value + 1 }
        assertEquals(2, values.size)
        assertEquals(2, values[0])
        assertEquals(3, values[1])
    }

    @Test
    fun verifyMultiIntIterableInlineMap() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = MultiIntIterable(initialValue)
        val newValues = singleIntIterable.inlineMap { value -> value + 1 }
        val values = mutableListOf<Int>()
        for (value in newValues) {
            values.add(value)
        }
        assertEquals(2, values.size)
        assertEquals(2, values[0])
        assertEquals(3, values[1])
    }

    @Test
    fun verifyMultiIntIterableFold() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = MultiIntIterable(initialValue)
        val result = singleIntIterable.fold(0) { result: Int, value: Int -> result + value }
        assertEquals(3, result)
    }

    @Test
    fun verifyMultiIntIterableInlineFold() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = MultiIntIterable(initialValue)
        val result = singleIntIterable.inlineFold(0) { result: Int, value: Int -> result + value }
        assertEquals(3, result)
    }

    @Test
    fun verifyMultiIntIterableFilter() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = MultiIntIterable(initialValue)
        val values = singleIntIterable.filter { value -> value % 2 == 0 }
        assertEquals(1, values.size)
        assertEquals(2, values[0])
    }
}
