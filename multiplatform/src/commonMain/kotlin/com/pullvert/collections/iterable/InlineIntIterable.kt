package com.pullvert.collections

import com.pullvert.io.Serializable

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
expect inline class InlineIntIterable @PublishedApi internal constructor(
        @PublishedApi internal val array: IntArray
) : Iterable<Int>, Serializable {
    override fun iterator(): IntIterator
}

/**
 * build an array from the given range, and wrap it in a [InlineIntIterable]
 */
inline fun IntRange.toInlineIntIterable(): InlineIntIterable {
    val intArray = IntArray(endInclusive - start + 1)
    for ((index, value) in this.withIndex()) {
        intArray[index] = value
    }
    return InlineIntIterable(intArray)
}

/**
 * wrap the given array in a [InlineIntIterable]
 */
inline fun IntArray.toInlineIntIterable() = InlineIntIterable(this)

/**
 * Creates a new InlineIntIterable of the specified [size], where each element is calculated by calling the specified
 * [init] function.
 *
 * The function [init] is called for each InlineIntIterable element sequentially starting from the first one.
 * It should return the value for an InlineIntIterable element given its index.
 */
inline fun newInlineIntIterable(size: Int, noinline init: (Int) -> Int) = InlineIntIterable(IntArray(size, init))

/**
 * Performs the given [action] on each element directly in the original array.
 */
inline fun InlineIntIterable.inlineForEach(action: (Int) -> Unit) {
    for (element in array) action(element)
}

/**
 * Returns a new InlineIntIterable containing the results of applying the given [transform] function
 * to each element and storing results in a new array.
 */
inline fun InlineIntIterable.inlineMap(transform: (Int) -> Int): InlineIntIterable {
    val newArray = IntArray(array.size)
    for ((index, value) in array.withIndex()) {
        newArray[index] = transform(value)
    }
    return InlineIntIterable(newArray)
}

/**
 * Accumulates value starting with [initial] value and applying [operation] from left to right to current accumulator value and each element.
 */
inline fun <T> InlineIntIterable.inlineFold(initial: T, operation: (acc: T, Int) -> T): T {
    var accumulator = initial
    for (element in array) accumulator = operation(accumulator, element)
    return accumulator
}

/**
 * Returns an Iterable containing only elements matching the given [predicate].
 */
inline fun InlineIntIterable.inlineFilter(predicate: (Int) -> Boolean): InlineIntIterable {
    val newArray = IntArray(array.size)
    var index = -1
    for (element in array) {
        if (predicate(element)) {
            newArray[++index] = element
        }
    }
    return InlineIntIterable(newArray.sliceArray(0..index))
}

// Specific operations on Int

/**
 * Returns the sum of all elements in the collection.
 */
public fun InlineIntIterable.inlineSum(): Int {
    var sum: Int = 0
    for (element in array) {
        sum += element
    }
    return sum
}
