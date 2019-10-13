/*
 * Copyright 2016-2019 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.pullvert.collections.benchmarks.scrabble

import com.pullvert.collections.InlineIntIterable
import com.pullvert.collections.inlineFold
import com.pullvert.collections.newInlineIntIterable
import com.pullvert.collections.toInlineIntIterable
import org.openjdk.jmh.annotations.*
import java.lang.Long.*
import java.lang.Long.max
import java.util.*
import java.util.concurrent.*
import kotlin.math.*

// code inspired from
// https://github.com/akarnokd/akarnokd-misc-kotlin/blob/master/src/main/kotlin/hu/akarnokd/kotlin/scrabble/ScrabbleKotlin.kt#L195

@Warmup(iterations = 7, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 7, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
open class SequencePlaysScrabbleBase : ShakespearePlaysScrabble() {

    @Benchmark
    public override fun play(): List<Map.Entry<Int, List<String>>> {

        val scoreOfALetter : (Int) -> Int = { letter -> letterScores[letter - 'a'.toInt()] }

//        val letterScore = { entry: Map.Entry<Int, LongWrapper> ->
//            letterScores[entry.key - 'a'.toInt()] * Integer.min(
//                    entry.value.get().toInt(),
//                    scrabbleAvailableLetters[entry.key - 'a'.toInt()]
//            )
//        }

        val letterScore = { entry: Map.Entry<Int, Long> ->
            letterScores[entry.key - 'a'.toInt()] *
                    min(entry.value.toInt(), scrabbleAvailableLetters[entry.key - 'a'.toInt()])
        }

//        val toInlineIntIterable: (String) -> InlineIntIterable = { string ->
//            string.chars().toArray().toInlineIntIterable()
////            newInlineIntIterable(string.length) { index -> string[index].toInt() }
//        }

        val histoOfLetters = { word: String ->
            word.asSequence()
                    .groupBy(Char::toInt)
                    .mapValues { it.value.sumBy { 1 }.toLong() }
        }

        val blank = { entry: Map.Entry<Int, Long> ->
            max(0, entry.value - scrabbleAvailableLetters[entry.key - 'a'.toInt()])
        }

//        val blank = { entry: Map.Entry<Int, LongWrapper> ->
//            max(0L, entry.value.get() - scrabbleAvailableLetters[entry.key - 'a'.toInt()])
//        }

        val nBlanks = { word: String ->
            histoOfLetters(word)
                    .map(blank)
                    .sum()
        }

        val checkBlanks = { word: String ->
            nBlanks(word) <= 2
        }

        val score2 = { word: String ->
            histoOfLetters(word)
                    .map(letterScore)
                    .sum()
        }

        val first3 = { word: String ->
            IterableSpliterator.of(word.chars().boxed().limit(3).spliterator()).asFlow()
        }

        val last3 = { word: String ->
            IterableSpliterator.of(word.chars().boxed().skip(3).spliterator()).asFlow()
        }

        val toBeMaxed = { word: String -> flowOf(first3(word), last3(word)).flattenConcat() }

        // Bonus for double letter
        val bonusForDoubleLetter = { word: String ->
            flow {
                emit(toBeMaxed(word)
                    .flatMapConcat { scoreOfALetter(it) }
                    .reduce { a, b -> max(a, b) })
            }
        }

        val score3 = { word: String ->
            flow {
                emit(flowOf(
                    score2(word), score2(word),
                    bonusForDoubleLetter(word),
                    bonusForDoubleLetter(word),
                    flowOf(if (word.length == 7) 50 else 0)
                ).flattenConcat().reduce { a, b -> a + b })
            }
        }

        val buildHistoOnScore: (((String) -> Flow<Int>) -> Flow<TreeMap<Int, List<String>>>) = { score ->
            flow {
                emit(shakespeareWords.asFlow()
                    .filter({ scrabbleWords.contains(it) && checkBlanks(it).single() })
                    .fold(TreeMap<Int, List<String>>(Collections.reverseOrder())) { acc, value ->
                        val key = score(value).single()
                        var list = acc[key] as MutableList<String>?
                        if (list == null) {
                            list = ArrayList()
                            acc[key] = list
                        }
                        list.add(value)
                        acc
                    })
            }
        }

        return runBlocking {
            buildHistoOnScore(score3)
                .flatMapConcat { map -> map.entries.iterator().asFlow() }
                .take(3)
                .toList()
        }
    }
}
