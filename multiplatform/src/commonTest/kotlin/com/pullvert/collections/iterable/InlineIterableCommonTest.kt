package com.pullvert.collections

import kotlin.test.*

class InlineIterableCommonTest {

    @Test
    fun verifyInlineIterableFromVararg() {
        val singleIterable = InlineIterable.of(1, 2)
        val values = mutableListOf<Int>()
        for (value in singleIterable) {
            values.add(value)
        }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyInlineIterableFromArray() {
        val singleIterable = InlineIterable.fromArray(arrayOf(1, 2))
        val values = mutableListOf<Int>()
        for (value in singleIterable) {
            values.add(value)
        }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyInlineIterableArrayExtensionFun() {
        val singleIterable = arrayOf(1, 2).toInlineIterable()
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
        val singleIterable = InlineIterable.of(1, 2)
        val values = mutableListOf<Int>()
        singleIterable.forEach { value -> values.add(value) }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyInlineIterableInlineForEach() {
        val singleIterable = InlineIterable.of(1, 2)
        val values = mutableListOf<Int>()
        singleIterable.inlineForEach { value -> values.add(value) }
        assertEquals(2, values.size)
        assertEquals(1, values[0])
        assertEquals(2, values[1])
    }

    @Test
    fun verifyInlineIterableMap() {
        val singleIterable = InlineIterable.of(1, 2)
        val values = singleIterable.map { value -> value + 1 }
        assertEquals(2, values.size)
        assertEquals(2, values[0])
        assertEquals(3, values[1])
    }

    @Test
    fun verifyInlineIterableInlineMap() {
        val singleIterable = InlineIterable.of(1, 2)
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
        val singleIterable = InlineIterable.of(1, 2)
        val result = singleIterable.fold(0) { result: Int, value: Int -> result + value }
        assertEquals(3, result)
    }

    @Test
    fun verifyInlineIterableInlineFold() {
        val singleIterable = InlineIterable.of(1, 2)
        val result = singleIterable.inlineFold(0) { result: Int, value: Int -> result + value }
        assertEquals(3, result)
    }

    @Test
    fun verifyInlineIterableFilter() {
        val singleIterable = InlineIterable.of(1, 2)
        val values = singleIterable.filter { value -> value % 2 == 0 }
        assertEquals(1, values.size)
        assertEquals(2, values[0])
    }

    @Test
    fun verifyInlineIterableInlineFilter() {
        val singleIterable = InlineIterable.of(1, 2)
        val newValues = singleIterable.inlineFilter { value -> value % 2 == 0 }
        val values = mutableListOf<Int>()
        for (value in newValues) {
            values.add(value)
        }
        assertEquals(1, values.size)
        assertEquals(2, values[0])
    }
}
