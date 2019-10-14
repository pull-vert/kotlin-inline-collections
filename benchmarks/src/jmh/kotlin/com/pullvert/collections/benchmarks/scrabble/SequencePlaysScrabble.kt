/*
 * Copyright 2016-2019 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package com.pullvert.collections.benchmarks.scrabble

import org.openjdk.jmh.annotations.*
import java.lang.Long.max
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.*

// code inspired from
// https://github.com/akarnokd/akarnokd-misc-kotlin/blob/master/src/main/kotlin/hu/akarnokd/kotlin/scrabble/ScrabbleKotlin.kt#L195

// ./gradlew --no-daemon cleanJmhJar jmh -Pjmh="SequencePlaysScrabble"


@Warmup(iterations = 7, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 7, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
open class SequencePlaysScrabble : ShakespearePlaysScrabble() {

    @Benchmark
    override fun play(): List<Map.Entry<Int, List<String>>> {

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
            word.asSequence().take(3).map(Char::toInt)
        }

        val last3 = { word: String ->
            word.asSequence().drop(max(0, word.length - 4)).map(Char::toInt)
        }

        val toBeMaxed = { word: String ->
            sequenceOf(first3(word), last3(word)).flatMap {v -> v}
        }

        // Bonus for double letter
        val bonusForDoubleLetter = { word: String ->
            toBeMaxed(word)
                    .map(scoreOfALetter)
                    .max() ?: 0
        }

        val score3 = { word: String ->
            (2 * score2(word)) + (2 * bonusForDoubleLetter(word)) +
                    (if (word.length == 7) 50 else 0)
        }

        val buildHistoOfLetters : ((String) -> Int) -> Map<Int, List<String>> = { score ->
            shakespeareWords
                    .filter { word -> scrabbleWords.contains(word) }
                    .filter(checkBlanks)
                    .groupByTo(TreeMap(Comparator.reverseOrder()), score)
        }

        return buildHistoOfLetters(score3)
                .entries
                .take(3)

        /*
        val finalList = sequenceOf("jezebel")
                .filter { word -> scrabbleWords.contains(word) }
                .filter(checkBlanks)
                .groupByTo(TreeMap(Comparator.reverseOrder()), score3)
        */

        // finalList.forEach { println(it) }
    }
}
