package com.pullvert.collections

import com.pullvert.io.Serializable

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
expect inline class InlineIterable<T> @PublishedApi internal constructor(
        // will be an Array<T>
        @PublishedApi internal val array: Any
) : Iterable<T>, Serializable {
    override fun iterator(): Iterator<T>

    /**
     * Companion object for [InlineIterable] class that contains its constructor functions
     */
    companion object
}

/**
 * wrap the given array in a [InlineIntIterable]
 */
inline fun <T : Any?> Array<T>.toInlineIterable() = InlineIterable<T>(this)

inline fun <T> InlineIterable.Companion.of(vararg value: T) = InlineIterable<T>(value)

inline fun <T> InlineIterable.Companion.fromArray(array: Array<T>) = InlineIterable<T>(array)

/**
 * Performs the given [action] on each element directly in the original array.
 */
inline fun <T> InlineIterable<T>.inlineForEach(action: (T) -> Unit) {
    for (element in array as Array<T>) action(element)
}

/**
 * Returns a new InlineIterable containing the results of applying the given [transform] function
 * to each element and storing results in a new array.
 */
inline fun <T, reified R> InlineIterable<T>.inlineMap(transform: (T) -> R): InlineIterable<R> {
    array as Array<T>
    val newArray = arrayOfNulls<R>(array.size)
    for ((index, value) in array.withIndex()) {
        newArray[index] = transform(value)
    }
    return InlineIterable(newArray)
}

/**
 * Accumulates value starting with [initial] value and applying [operation] from left to right to current accumulator value and each element.
 */
inline fun <T, U> InlineIterable<T>.inlineFold(initial: U, operation: (acc: U, T) -> U): U {
    var accumulator = initial
    for (element in array as Array<T>) accumulator = operation(accumulator, element)
    return accumulator
}

/**
 * Returns an Iterable containing only elements matching the given [predicate].
 */
inline fun <reified T> InlineIterable<T>.inlineFilter(predicate: (T) -> Boolean): InlineIterable<T> {
    array as Array<T>
    val newArray = arrayOfNulls<T>(array.size)
    var index = -1
    for (element in array) {
        if (predicate(element)) {
            newArray[++index] = element
        }
    }
    return InlineIterable(newArray.sliceArray(0..index))
}
