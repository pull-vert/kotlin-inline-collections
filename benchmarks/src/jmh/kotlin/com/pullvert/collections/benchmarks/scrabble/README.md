## Scrabble benchmarks

This package contains scrabble benchmarks.

Scrabble benchmarks were originally developed by José Paumard and are [available](https://github.com/JosePaumard/jdk8-stream-rx-comparison-reloaded) under Apache 2.0,
All benchmarks are based on (or copied from) [David Karnok work](https://github.com/akarnokd/akarnokd-misc-kotlin) or [kotlinx-coroutines versions](https://github.com/Kotlin/kotlinx.coroutines/tree/master/benchmarks/src/jmh/kotlin/benchmarks/flow/scrabble)

### Benchmark classes

  * `SequencePlaysScrabble` is a version of benchmark built on top of `Sequence`.
     
### Results

```
FlowPlaysScrabbleBase.play    avgt   14   94.845 ± 1.345  ms/op
FlowPlaysScrabbleOpt.play     avgt   14   20.587 ± 0.173  ms/op

RxJava2PlaysScrabble.play     avgt   14  114.253 ± 3.450  ms/op
RxJava2PlaysScrabbleOpt.play  avgt   14   30.795 ± 0.144  ms/op

SaneFlowPlaysScrabble.play    avgt   14   18.825 ± 0.231  ms/op
SequencePlaysScrabble.play    avgt   14   13.787 ± 0.111  ms/op
```
