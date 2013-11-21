Java Enumerable
----

This is an implementation of deferred lazy collections using Java. Inspired by F#, C#, and Haskell. The goal is to make working with iterable items easier. Most list processing is a series of transformations and iterations. By leveraging simple and composable functions you can reduce edge cases, off by 1 errors, extraneous processing and unintentional performance bottlenecks. Also, you can easily create (and end) infinite lazy streams.

Currently JEnumerable provides

- Map
- Filter
- FlatMap
- OrderBy
- Take
- TakeWhile
- Skip
- SkipWhile
- Iter
- Iteri
- DistinctUnion
- Distinct
- DistinctBy
- Intersect
- Except
- First
- Last
- Nth
- Fold
- FoldWithDefaultSeed
- Any
- All
- Windowed
- Tails   
- Pairwise
- GroupRuns
- Intersperse
- Intercalate
- ToDictionary
- ToGroupedDictionary


And infinite sequence generation with

- Yield
- Yield break
- Yield bang

Some functionality exists in Java 8 streams, but more is here. Initial benchmarks show that JEnumerable is comporable in speed to JDK8 streams. JDK8 streams, however, does parallelized optimizations which this does not, so can be bettter performant sometimes.  

Check here for [examples](enumerable/src/test/java/TestEnumerable.java).
