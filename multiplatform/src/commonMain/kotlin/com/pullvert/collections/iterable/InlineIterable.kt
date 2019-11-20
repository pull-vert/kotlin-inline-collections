package com.pullvert.collections

import com.pullvert.io.Serializable

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
expect inline class InlineIterable<T> @PublishedApi internal constructor(
        @PublishedApi internal val array: Array<T>
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
inline fun <T> Array<T>.toInlineIterable() = InlineIterable(this)

inline fun <reified T> InlineIterable.Companion.of(vararg value: T): InlineIterable<out T> = InlineIterable(value)

inline fun <T> InlineIterable.Companion.fromArray(array: Array<T>): InlineIterable<out T> = InlineIterable(array)

/**
 * Performs the given [action] on each element directly in the original array.
 */
inline fun <T> InlineIterable<T>.inlineForEach(action: (T) -> Unit) {
    for (element in array) action(element)
}

/**
 * Returns a new InlineIterable containing the results of applying the given [transform] function
 * to each element and storing results in a new array.
 */
inline fun <T, reified R> InlineIterable<T>.inlineMap(transform: (T) -> R): InlineIterable<R> {

    val newArray = arrayOfNulls<R>(array.size)
    for ((index, value) in array.withIndex()) {
        newArray[index] = transform(value)
    }
    return InlineIterable(newArray as Array<R>)
}

/**
 * Accumulates value starting with [initial] value and applying [operation] from left to right to current accumulator value and each element.
 */
inline fun <T, U> InlineIterable<T>.inlineFold(initial: U, operation: (acc: U, T) -> U): U {
    var accumulator = initial
    for (element in array) accumulator = operation(accumulator, element)
    return accumulator
}

/**
 * Returns an Iterable containing only elements matching the given [predicate].
 */
inline fun <reified T> InlineIterable<T>.inlineFilter(predicate: (T) -> Boolean): InlineIterable<T> {
    val newArray = arrayOfNulls<T>(array.size)
    var index = -1
    for (element in array) {
        if (predicate(element)) {
            newArray[++index] = element
        }
    }
    return InlineIterable((newArray as Array<T>).sliceArray(0..index))
}
