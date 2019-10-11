package com.pullvert.collections.benchmarks

import com.pullvert.collections.inlineFilter
import com.pullvert.collections.inlineFold
import com.pullvert.collections.toInlineIntIterable
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

fun Int.isGood() = this % 4 == 0
const val N = 1_000_000

// ./gradlew --no-daemon cleanJmhJar jmh -Pjmh="RangeFilterSumBenchmark"

//Benchmark                                             Mode  Cnt     Score     Error  Units
//RangeFilterSumBenchmark.testArrayToInlineIntIterable  avgt    5  2485,893 ▒ 103,244  us/op
//RangeFilterSumBenchmark.testIntArray                  avgt    5  5118,374 ▒ 416,788  us/op
//RangeFilterSumBenchmark.testIntRange                  avgt    5  8918,876 ▒ 459,739  us/op
//RangeFilterSumBenchmark.testLoopOnArray               avgt    5  1084,504 ▒  17,004  us/op
//RangeFilterSumBenchmark.testLoopOnRange               avgt    5  1150,670 ▒  74,086  us/op
//RangeFilterSumBenchmark.testRangeToInlineIntIterable  avgt    5  4173,115 ▒ 108,183  us/op


@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
open class RangeFilterSumBenchmark {

    val range = 1..N
    lateinit var rangeArray: IntArray

    @Setup
    fun setup() {
        rangeArray = IntArray(range.endInclusive - range.start + 1)
        for ((index, value) in range.withIndex()) {
            rangeArray[index] = value
        }
    }

    @Benchmark
    fun testLoopOnRange(): Int {
        var sum = 0
        for (i in range) {
            if (i.isGood())
                sum += i
        }
        return sum
    }

    @Benchmark
    fun testLoopOnArray(): Int {
        var sum = 0
        for (i in rangeArray) {
            if (i.isGood())
                sum += i
        }
        return sum
    }

    @Benchmark
    fun testIntRange(): Int =
            range.filter { it.isGood() }
                    .fold(0, { a, b -> a + b })

    @Benchmark
    fun testIntArray(): Int =
            rangeArray.filter { it.isGood() }
                    .fold(0, { a, b -> a + b })

    @Benchmark
    fun testRangeToInlineIntIterable(): Int =
            range.toInlineIntIterable()
                    .inlineFilter { it.isGood() }
                    .inlineFold(0, { a, b -> a + b })

    @Benchmark
    fun testArrayToInlineIntIterable(): Int =
            rangeArray.toInlineIntIterable()
                    .inlineFilter { it.isGood() }
                    .inlineFold(0, { a, b -> a + b })
}
