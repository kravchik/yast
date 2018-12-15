# yast benchmarks

This module contains JMH benchmarks for the parsers

Note: you'd better shut down all the applications and connect notebook to the power for reproducible results.

Random results from Java 1.8, 2,6 GHz Intel Core i7

```
Benchmark                                     Mode  Cnt      Score      Error   Units
ParserBenchmark.intellij                      avgt    7     17,497 ±    0,251   us/op
ParserBenchmark.intellij:·gc.alloc.rate.norm  avgt    7   8968,534 ±    0,035    B/op
ParserBenchmark.javacc                        avgt    7     67,014 ±    3,684   us/op
ParserBenchmark.javacc:·gc.alloc.rate.norm    avgt    7  44435,278 ±   19,359    B/op
```
