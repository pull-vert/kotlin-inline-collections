package com.pullvert.collections

import com.pullvert.io.Serializable

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
expect inline class MultiIntIterable @PublishedApi internal constructor(
        @PublishedApi internal val array: IntArray
) : Iterable<Int>, Serializable {
    override fun iterator(): IntIterator
}

/**
 * build an array from the given range, and wrap it in a [MultiIntIterable]
 */
public inline fun IntRange.toInlineIntIterable(): MultiIntIterable {
    val intArray = IntArray(endInclusive - start + 1)
    for ((index, value) in this.withIndex()) {
        intArray[index] = value
    }
    return MultiIntIterable(intArray)
}

/**
 * wrap the given array in a [MultiIntIterable]
 */
public inline fun IntArray.toInlineIntIterable() = MultiIntIterable(this)

/**
 * Performs the given [action] on each element directly in the original array.
 */
inline fun MultiIntIterable.inlineForEach(action: (Int) -> Unit) {
    for (element in array) action(element)
}

/**
 * Returns a new MultiIntIterable containing the results of applying the given [transform] function
 * to each element directly in the original array.
 */
inline fun MultiIntIterable.inlineMap(transform: (Int) -> Int): MultiIntIterable {
    val newArray = IntArray(array.size)
    for ((index, value) in array.withIndex()) {
        newArray[index] = transform(value)
    }
    return MultiIntIterable(newArray)
}

/**
 * Accumulates value starting with [initial] value and applying [operation] from left to right to current accumulator value and each element.
 */
inline fun <T> MultiIntIterable.inlineFold(initial: T, operation: (acc: T, Int) -> T): T {
    var accumulator = initial
    for (element in array) accumulator = operation(accumulator, element)
    return accumulator
}

/**
 * Returns an Iterable containing only elements matching the given [predicate].
 */
inline fun MultiIntIterable.inlineFilter(predicate: (Int) -> Boolean): MultiIntIterable {
    val newArray = IntArray(array.size)
    var index = -1
    for (element in array) {
        if (predicate(element)) {
            newArray[++index] = element
        }
    }
    return MultiIntIterable(newArray.sliceArray(0..index))
}
