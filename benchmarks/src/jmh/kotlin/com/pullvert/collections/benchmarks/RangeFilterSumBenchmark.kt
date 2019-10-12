package com.pullvert.collections.benchmarks

import com.pullvert.collections.inlineFilter
import com.pullvert.collections.inlineFold
import com.pullvert.collections.toInlineIntIterable
import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

fun Int.isGood() = this % 4 == 0
const val N = 1_000_000

// ./gradlew --no-daemon cleanJmhJar jmh -Pjmh="RangeFilterSumBenchmark"

//Benchmark                                             Mode  Cnt      Score      Error  Units
//RangeFilterSumBenchmark.testLoopOnArray               avgt    5   1122,737 ▒   27,060  us/op
//RangeFilterSumBenchmark.testLoopOnRange               avgt    5   1141,871 ▒   67,917  us/op
//RangeFilterSumBenchmark.testArrayToInlineIntIterable  avgt    5   2568,473 ▒   83,961  us/op
//RangeFilterSumBenchmark.testArray                     avgt    5   5091,540 ▒  312,059  us/op
//RangeFilterSumBenchmark.testRangeToInlineIntIterable  avgt    5   4222,177 ▒  166,302  us/op
//RangeFilterSumBenchmark.testRange                     avgt    5   8952,504 ▒  189,197  us/op
//RangeFilterSumBenchmark.testArrayToList               avgt    5  16377,306 ▒ 2399,851  us/op
//RangeFilterSumBenchmark.testArrayToJavaArrayList      avgt    5  16920,373 ▒  815,853  us/op
//RangeFilterSumBenchmark.testRangeToList               avgt    5  20426,253 ▒ 1106,922  us/op
//RangeFilterSumBenchmark.testArrayToListViaStream      avgt    5  24046,213 ▒ 1773,994  us/op



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

    private fun arrayToArrayList(): ArrayList<Int> {
        val arrayList = ArrayList<Int>(rangeArray.size)
        for (i in rangeArray) {
            arrayList.add(i)
        }
        return arrayList
    }

    private fun arrayToListViaStream() =
            Arrays.stream(rangeArray)
                    .boxed()
                    .collect(Collectors.toList())

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
    fun testRange(): Int =
            range.filter { it.isGood() }
                    .fold(0, { a, b -> a + b })

    @Benchmark
    fun testArray(): Int =
            rangeArray.filter { it.isGood() }
                    .fold(0, { a, b -> a + b })

    @Benchmark
    fun testRangeToList(): Int =
            range.toList()
                    .filter { it.isGood() }
                    .fold(0, { a, b -> a + b })

    @Benchmark
    fun testArrayToList(): Int =
            rangeArray.toList().toList()
                    .filter { it.isGood() }
                    .fold(0, { a, b -> a + b })

    @Benchmark
    fun testArrayToJavaArrayList(): Int =
            arrayToArrayList()
                    .filter { it.isGood() }
                    .fold(0, { a, b -> a + b })

    @Benchmark
    fun testArrayToListViaStream(): Int =
            arrayToListViaStream()
                    .filter { it.isGood() }
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
