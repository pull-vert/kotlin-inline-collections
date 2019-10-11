package com.pullvert.collections

import kotlin.test.*

class InlineIntIterableCommonTest {

    @Test
    fun verifyInlineIntIterable() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = InlineIntIterable(initialValue)
        val values = mutableListOf<Int>()
        for (value in singleIntIterable) {
            values.add(value)
        }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyRangeToInlineIntIterable() {
        val initialValue = 1..2
        val intIterable = initialValue.toInlineIntIterable()
        val values = mutableListOf<Int>()
        for (value in intIterable) {
            values.add(value)
        }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyInlineIntIterableForEach() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = InlineIntIterable(initialValue)
        val values = mutableListOf<Int>()
        singleIntIterable.forEach { value -> values.add(value) }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyInlineIntIterableInlineForEach() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = InlineIntIterable(initialValue)
        val values = mutableListOf<Int>()
        singleIntIterable.inlineForEach { value -> values.add(value) }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyInlineIntIterableMap() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = InlineIntIterable(initialValue)
        val values = singleIntIterable.map { value -> value + 1 }
        assertEquals(2, values.size)
        assertEquals(2, values[0])
        assertEquals(3, values[1])
    }

    @Test
    fun verifyInlineIntIterableInlineMap() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = InlineIntIterable(initialValue)
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
    fun verifyInlineIntIterableFold() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = InlineIntIterable(initialValue)
        val result = singleIntIterable.fold(0) { result: Int, value: Int -> result + value }
        assertEquals(3, result)
    }

    @Test
    fun verifyInlineIntIterableInlineFold() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = InlineIntIterable(initialValue)
        val result = singleIntIterable.inlineFold(0) { result: Int, value: Int -> result + value }
        assertEquals(3, result)
    }

    @Test
    fun verifyInlineIntIterableFilter() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = InlineIntIterable(initialValue)
        val values = singleIntIterable.filter { value -> value % 2 == 0 }
        assertEquals(1, values.size)
        assertEquals(2, values[0])
    }

    @Test
    fun verifyInlineIntIterableInlineFilter() {
        val initialValue = intArrayOf(1, 2)
        val singleIntIterable = InlineIntIterable(initialValue)
        val newValues = singleIntIterable.inlineFilter { value -> value % 2 == 0 }
        val values = mutableListOf<Int>()
        for (value in newValues) {
            values.add(value)
        }
        assertEquals(1, values.size)
        assertEquals(2, values[0])
    }
}
