Java Enumerable
----

This is an implementation of deferred lazy collections using Java. Inspired by F#, C#, and Haskell. The goal is to make working with iterable items easier. Most list processing is a series of transformations and iterations. By leveraging simple and composable functions you can reduce edge cases, off by 1 errors, extraneous processing and unintentional performance bottlenecks. Also, you can easily create (and end) infinite lazy streams.

Currently JEnumerable provides

- Map
- Filter
- FlatMap
- Order
- OrderBy
- OrderDesc
- OrderByDesc
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
- Range
- Min
- Max
- MinBy
- MaxBy


And infinite sequence generation with

- Yield
- Yield break
- Yield bang

Some functionality exists in Java 8 streams, but more is here. Initial benchmarks show that JEnumerable is comporable in speed to JDK8 streams. JDK8 streams, however, does parallelized optimizations which this does not, so can be bettter performant sometimes.  

Check here for [examples](enumerable/src/test/java/TestEnumerable.java).

Benchmarks
---
Initial benchmarks are as follows.  The first element is the native streams function. The second element is JEnumerable's performance in milliseconds.  As you can see JEnumerable is reasonably comporable, sometimes slower, sometimes faster, but for reasonably sized lists the performance is equal.  This is just an example, since many functions in JEnumerable are not duplicated in Java 8 streams and so can't be comporably benchmarked.  



Element# 	  | Reduce 		  | Map 	| Min 	| Order  | Distinct | Filter
-----------   | ------------- | ------ 	| ----- | ------ | -------- | -----
1  | (0,0)|(0,0)|(0,0)|(0,0)|(0,0)|(0,0)
10  | (0,0)|(0,0)|(0,0)|(0,0)|(0,0)|(0,0)
100  |(0,0)|(0,0)|(0,0)|(0,0)|(0,0)|(0,0)
1000  |(0,0)|(0,0)|(0,0)|(0,2)|(1,1)|(0,0)
10000  |(1,2)|(1,2)|(0,3)|(2,14)|(3,3)|(1,2)
100000  |(3,10)|(6,9)|(1,14)|(9,92)|(7,11)|(2,4)
1000000 | (11,20) | (16,73) | (13, 22) | (41, 1080)|(386, 75)|(15, 26)
10000000 | (555,437) | (10253, 636) | (118, 184) | (165, 15122)|(1485, 4417)|(146, 266)

Check here for [the benchmark code](enumerable/src/test/java/TestBenchmarks.java)