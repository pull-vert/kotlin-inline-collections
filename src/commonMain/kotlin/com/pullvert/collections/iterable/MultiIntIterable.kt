package com.pullvert.collections

import com.pullvert.io.Serializable

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
expect inline class MultiIntIterable internal constructor(internal val values: IntArray) : Iterable<Int>, Serializable {
    override fun iterator(): IntIterator
}
