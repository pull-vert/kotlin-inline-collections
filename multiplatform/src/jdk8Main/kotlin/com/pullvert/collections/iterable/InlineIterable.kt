package com.pullvert.collections

import java.io.Serializable
import java.util.*


@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
actual inline class InlineIterable<T> @PublishedApi internal actual constructor(
        @PublishedApi internal val array: Any
) : Iterable<T>, Serializable {

    actual override fun iterator() = (array as Array<T>).iterator()

    /**
     * Companion object for [InlineIterable] class that contains its constructor functions
     */
    actual companion object {}

    /**
     * TODO make a better implementation
     */
    override fun spliterator(): Spliterator<T> {
        return Spliterators.spliteratorUnknownSize((array as Array<T>).iterator(), 0)
    }

//    fixme : hashcode/equals not supported in 1.3.50 (nor 1.3.60-eap-22 nor 1.3.70-dev-260)
//    companion object {
//        private const val serialVersionUID: Long = 1L
//    }
//
//    override fun hashCode(): Int {
//        return Arrays.hashCode(values)
//    }
}