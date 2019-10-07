package com.pullvert.collections

import java.io.Serializable
import java.util.*

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
actual inline class MultiIntIterable internal actual constructor(internal val values: IntArray) : Iterable<Int>, Serializable {

    actual override fun iterator() = values.iterator()

    /**
     * TODO make a better implementation
     */
    override fun spliterator(): Spliterator<Int> {
        return Spliterators.spliteratorUnknownSize<Int>(iterator(), 0)
    }
}