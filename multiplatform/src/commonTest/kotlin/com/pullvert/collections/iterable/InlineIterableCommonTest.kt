package com.pullvert.collections

import kotlin.test.*

class InlineIterableCommonTest {

    @Test
    fun verifyInlineIterable() {
        val initialValue = arrayOf(1, 2)
        val singleIterable = InlineIterable(initialValue)
        val values = mutableListOf<Int>()
        for (value in singleIterable) {
            values.add(value)
        }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyInlineIterableForEach() {
        val initialValue = arrayOf(1, 2)
        val singleIterable = InlineIterable(initialValue)
        val values = mutableListOf<Int>()
        singleIterable.forEach { value -> values.add(value) }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyInlineIterableInlineForEach() {
        val initialValue = arrayOf(1, 2)
        val singleIterable = InlineIterable(initialValue)
        val values = mutableListOf<Int>()
        singleIterable.inlineForEach { value -> values.add(value) }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyInlineIterableMap() {
        val initialValue = arrayOf(1, 2)
        val singleIterable = InlineIterable(initialValue)
        val values = singleIterable.map { value -> value + 1 }
        assertEquals(2, values.size)
        assertEquals(2, values[0])
        assertEquals(3, values[1])
    }

    @Test
    fun verifyInlineIterableInlineMap() {
        val initialValue = arrayOf(1, 2)
        val singleIterable = InlineIterable(initialValue)
        val newValues = singleIterable.inlineMap { value -> value + 1 }
        val values = mutableListOf<Int>()
        for (value in newValues) {
            values.add(value)
        }
        assertEquals(2, values.size)
        assertEquals(2, values[0])
        assertEquals(3, values[1])
    }

    @Test
    fun verifyInlineIterableFold() {
        val initialValue = arrayOf(1, 2)
        val singleIterable = InlineIterable(initialValue)
        val result = singleIterable.fold(0) { result: Int, value: Int -> result + value }
        assertEquals(3, result)
    }

    @Test
    fun verifyInlineIterableInlineFold() {
        val initialValue = arrayOf(1, 2)
        val singleIterable = InlineIterable(initialValue)
        val result = singleIterable.inlineFold(0) { result: Int, value: Int -> result + value }
        assertEquals(3, result)
    }

    @Test
    fun verifyInlineIterableFilter() {
        val initialValue = arrayOf(1, 2)
        val singleIterable = InlineIterable(initialValue)
        val values = singleIterable.filter { value -> value % 2 == 0 }
        assertEquals(1, values.size)
        assertEquals(2, values[0])
    }

    @Test
    fun verifyInlineIterableInlineFilter() {
        val initialValue = arrayOf(1, 2)
        val singleIterable = InlineIterable(initialValue)
        val newValues = singleIterable.inlineFilter { value -> value % 2 == 0 }
        val values = mutableListOf<Int>()
        for (value in newValues) {
            values.add(value)
        }
        assertEquals(1, values.size)
        assertEquals(2, values[0])
    }
}
