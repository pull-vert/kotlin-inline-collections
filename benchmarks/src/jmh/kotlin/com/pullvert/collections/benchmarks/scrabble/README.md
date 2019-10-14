## Scrabble benchmarks

This package contains scrabble benchmarks.

Scrabble benchmarks were originally developed by José Paumard and are [available](https://github.com/JosePaumard/jdk8-stream-rx-comparison-reloaded) under Apache 2.0,
All benchmarks are based on (or copied from) [David Karnok work](https://github.com/akarnokd/akarnokd-misc-kotlin) or [kotlinx-coroutines versions](https://github.com/Kotlin/kotlinx.coroutines/tree/master/benchmarks/src/jmh/kotlin/benchmarks/flow/scrabble)

### Benchmark classes

  * `SequencePlaysScrabble` is a version of benchmark built on top of `Sequence`.
     
### Results

```
SequencePlaysScrabble.play  avgt    7  35,695 ▒ 0,446  ms/op
```
