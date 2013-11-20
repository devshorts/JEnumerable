Java Enumerable
----

This is an implementation of deferred lazy collections using Java.

The goal, to provide

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
- Yield/Yield break (and infinite sequence generation)

Some functionality exists in Java 8 streams, but more is here. Initial benchmarks show that JEnumerable is comporable in speed to JDK8 streams. JDK8 streams, however, does parallelized optimizations which this does not, so can be bettter performant sometimes.  

Check here for [examples](enumerable/src/test/java/TestEnumerable.java).
