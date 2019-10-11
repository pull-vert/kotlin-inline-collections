package com.pullvert.collections.benchmarks

import com.pullvert.collections.MultiIntIterable
import com.pullvert.collections.inlineFilter
import com.pullvert.collections.inlineFold
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

fun Int.isGood() = this % 4 == 0
const val N = 1_000_000

// ./gradlew --no-daemon cleanJmhJar jmh -Pjmh="RangeFilterSumBenchmark"




@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
open class RangeFilterSumBenchmark {

    @Benchmark
    fun testBaselineLoop(): Int {
        var sum = 0
        for (i in 1..N) {
            if (i.isGood())
                sum += i
        }
        return sum
    }

    @Benchmark
    fun testIntRange(): Int =
            (1..N).filter { it.isGood() }
                    .fold(0, { a, b -> a + b })

    @Benchmark
    fun testInlineIntIterable(): Int =
            MultiIntIterable(1..N) // todo MultiIntIterable from IntRange
                    .inlineFilter { it.isGood() }
                    .inlineFold(0, { a, b -> a + b })
}
