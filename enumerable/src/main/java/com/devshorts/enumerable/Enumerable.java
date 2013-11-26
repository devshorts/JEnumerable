package com.devshorts.enumerable;

import com.devshorts.enumerable.data.*;
import com.devshorts.enumerable.iterators.*;

import java.util.*;
import java.util.function.*;

import static java.util.Arrays.asList;

class YieldedEnumeration<TSource> implements Iterable<TSource>  {

    private Supplier<Yieldable<TSource>> generator;

    public YieldedEnumeration(Supplier<Yieldable<TSource>> generator) {
        super();

        this.generator = generator;
    }

    @Override
    public Iterator<TSource> iterator() {
        return new YieldedEnumerationIterator<>(generator);
    }
}

public class Enumerable<TSource> implements Iterable<TSource> {

    //private Iterable source;

    private Function<Iterable<TSource>, Iterator<TSource>> iteratorGenerator;

    public static <TSource> Enumerable<TSource> init(Iterable<TSource> source){
        return new Enumerable<>(_ig -> new EnumerableIterator<>(source));
    }

    public static Enumerable<Character> init(String source){
        Box<Integer> b = new Box<>(0);
        return generate(() -> {
            if(b.elem < source.length()){
                return Yieldable.yield(source.charAt(b.elem), () -> b.elem++);
            }
            return Yieldable.yieldBreak();
        });
    }

    public static <TSource> Enumerable<TSource> generate(Supplier<Yieldable<TSource>> generator){
        return new Enumerable<>(_ig -> new EnumerableIterator<>(new YieldedEnumeration<>(generator)));
    }

    public static <TSource> Enumerable<TSource> generate(Supplier<Yieldable<TSource>> generator,
                                                         Action onNewIterator){
        return new Enumerable<>(_ig -> {
            onNewIterator.exec();

            return new EnumerableIterator<>(new YieldedEnumeration<>(generator));
        });
    }

    private <TResult> Enumerable<TResult> enumerableWithIterator(Function<Iterable<TSource>, Iterator<TResult>> generator){
        return new Enumerable<>(_ig -> generator.apply(this));
    }

    protected Enumerable(Function<Iterable<TSource>, Iterator<TSource>> iteratorGenerator) {
        this.iteratorGenerator = iteratorGenerator;
    }

    public static Enumerable<Integer> range(Integer start, Integer end, Integer interval){
        Box<Integer> b = new Box(start);
        return Enumerable.generate(() -> {
            if(b.elem <= end){
                return Yieldable.yield(b.elem, () -> b.elem += interval);
            }
            return Yieldable.yieldBreak();
        });
    }

    public static Enumerable<Integer> range(Integer start, Integer end){
        return range(start, end, 1);
    }

    public <TResult> Enumerable<TResult> map(Function<TSource, TResult> mapFunc){
        return enumerableWithIterator(source ->
                new MapIterator<TSource, TResult>(source, i -> mapFunc.apply(i)));
    }

    public <TResult> Enumerable<TResult> flatMap(Function<TSource, List<TResult>> mapFunc){
        return enumerableWithIterator(source ->
                new FlatMapIterator<TSource, TResult>(source, i -> mapFunc.apply(i)));
    }

    public Enumerable<TSource> filter(Predicate<TSource> filterFunc){
        return enumerableWithIterator(source -> new FilterIterator<>(source, filterFunc));
    }

    public Enumerable<TSource> take(int n){
        return enumerableWithIterator(source -> new TakeIterator<>(source, n));
    }

    public Enumerable<TSource> takeWhile(Predicate<TSource> predicate){
        return enumerableWithIterator(source -> new TakeWhileIterator<>(source, predicate));
    }

    public Enumerable<TSource> skip(int skipNum){
        return enumerableWithIterator(source -> new SkipIterator<>(source, skipNum));
    }

    public Enumerable<TSource> skipWhile(Predicate<TSource> predicate){
        return enumerableWithIterator(source -> new SkipWhileIterator<>(source, predicate));
    }

    public Enumerable<List<TSource>> windowed(int n){
        return enumerableWithIterator(source -> new WindowedIterator<>(source, n));
    }

    public Enumerable<List<TSource>> tails(){
        return enumerableWithIterator(TailsIterator::new);
    }

    public Enumerable<List<TSource>> groupRuns(){
        return enumerableWithIterator(GroupRunsIterator::new);
    }

    public Enumerable<Tuple<TSource, TSource>> pairwise(){
        return enumerableWithIterator(PairwiseIterator::new);
    }

    public Enumerable<TSource> iter(Consumer<TSource> action){
        return enumerableWithIterator(source ->
                new IndexIterator<>(source, idxPair -> action.accept(idxPair.value)));
    }

    public Enumerable<TSource> iteri(BiConsumer<Integer, TSource> action){
        return enumerableWithIterator(source ->
                new IndexIterator<>(source, idxPair -> action.accept(idxPair.index, idxPair.value)));
    }

    public Enumerable<TSource> distinctUnion(Iterable<TSource> intersectWith){
        return enumerableWithIterator(source -> new DistinctUnion<>(source, intersectWith));
    }

    public Enumerable<TSource> intersect(Iterable<TSource> intersectWith){
        return enumerableWithIterator(source -> new IntersectIterator<>(source, intersectWith));
    }

    public Enumerable<TSource> except(Iterable<TSource> except){
        return enumerableWithIterator(source -> new ExceptIterator<>(source, except));
    }

    public Enumerable<TSource> intersperse(TSource element){
        return enumerableWithIterator(source -> new IntercalateIterator<>(source, asList(element)));
    }

    public Enumerable<TSource> intercalate(List<TSource> elements){
        return enumerableWithIterator(source -> new IntercalateIterator<>(source, elements));
    }

    public <TProjection> Enumerable<TSource> orderBy(Function<TSource, Comparable<TProjection>> projection){
        DefaultComparer comparer = new DefaultComparer();

        return orderBy(projection, comparer::compare);
    }

    public Enumerable<TSource> order(){
        return orderBy(i-> (Comparable<TSource>)i, new DefaultComparer());
    }

    public Enumerable<TSource> orderDesc(){
        return orderByDesc(i -> (Comparable<TSource>) i, new DefaultComparer());
    }

    public <TProjection> Enumerable<TSource> orderByDesc(Function<TSource, Comparable<TProjection>> projection){
        DefaultComparer comparer = new DefaultComparer();
        return orderBy(projection, (o1, o2) -> comparer.compare(o2, o1));
    }

    public <TProjection> Enumerable<TSource> orderByDesc(Function<TSource, Comparable<TProjection>> projection,
                                                         Comparator<TProjection> comparator){
        return orderBy(projection, (o1, o2) -> comparator.compare(o2, o1));
    }

    public <TProjection> Enumerable<TSource> orderBy(Function<TSource, Comparable<TProjection>> projection,
                                                     Comparator<TProjection> comparator){
        return enumerableWithIterator(source -> new OrderByIterator(source, projection, comparator));
    }

    public <TSecond, TProjection> Enumerable<TProjection> zip(Iterable<TSecond> zipWith,
                                                              BiFunction<TSource, TSecond, TProjection> zipper){
        return enumerableWithIterator(source -> new ZipIterator<>(source, zipWith, zipper));
    }

    public <TSecond, TThird, TProjection> Enumerable<TProjection> zip3(Iterable<TSecond> zip2,
                                                                        Iterable<TThird> zip3,
                                                                        Zip3Func<TSource, TSecond, TThird, TProjection> zipper){
        return enumerableWithIterator(zip1 -> new Zip3Iterator<>(zip1, zip2, zip3, zipper));
    }

    public TSource first(){
        return unsafeIterEval(new NthIterator<>(this, 1));
    }

    public <TProjection> TSource minBy(Function<TSource, TProjection> projector, Comparator<TProjection> comparer){
        return unsafeIterEval(new MinMaxBy<>(this, projector, (a, b) -> comparer.compare(b,a) == -1));
    }

    public <TProjection> TSource maxBy(Function<TSource, TProjection> projector, Comparator<TProjection> comparer){
        return unsafeIterEval(new MinMaxBy<>(this, projector, (a, b) -> comparer.compare(b, a) == 1));
    }

    public <TProjection> TSource minBy(Function<TSource, TProjection> projector){
        return (TSource)minBy(projector, new DefaultComparer());
    }

    public <TProjection> TSource maxBy(Function<TSource, TProjection> projector){
        return (TSource)maxBy(projector, new DefaultComparer());
    }

    private boolean lt(TSource current, TSource next, Comparator<TSource> comparer){
        return comparer.compare(current, next) == -1;
    }
    private boolean gt(TSource current, TSource next, Comparator<TSource> comparer){
        return comparer.compare(current, next) == 1;
    }

    public TSource min(){
        return (TSource)min(new DefaultComparer());
    }

    public TSource max(){
        return (TSource)max(new DefaultComparer());
    }

    public TSource min(Comparator<TSource> comparer){
        return unsafeIterEval(new MinMaxBy<TSource, TSource>(this, i -> i,
                (current, next) -> lt(next, current, comparer)));
    }

    public TSource max(Comparator<TSource> comparer){
        return unsafeIterEval(new MinMaxBy<TSource, TSource>(this, i -> i,
                (current, next) -> gt(next, current, comparer)));
    }

    public TSource nth(int n){
        return unsafeIterEval(new NthIterator<>(this, n));
    }

    public TSource last(){
        return unsafeIterEval(new LastIterator<>(this));
    }

    public <TAcc> TAcc fold(BiFunction<TAcc, TSource, TAcc> accumulator, TAcc seed){
        return evalUnsafeMapIterator(new FoldIterator<>(this, accumulator, seed));
    }


    /**
     * Folds using the first element as the seed
     * @param accumulator
     * @return
     */
    public TSource foldWithFirst(BiFunction<TSource, TSource, TSource> accumulator){
        return unsafeIterEval(new FoldWithDefaultSeedIterator<>(this, accumulator));
    }

    public Boolean any(Predicate<TSource> predicate){
        return evalUnsafeMapIterator(new PredicateIterator<>(
                this,
                predicate,
                (acc, elem) -> acc || elem,
                i -> i,
                () -> true,
                false
        ));
    }

    public Boolean all(Predicate<TSource> predicate){
        return evalUnsafeMapIterator(new PredicateIterator<>(
                this,
                predicate,
                (acc, elem) -> acc && elem,
                i -> !i,
                () -> false,
                true
        ));
    }

    public Enumerable<TSource> distinct(){
        return enumerableWithIterator(source ->
                new DistinctIterator<TSource, TSource>(source, i -> i));
    }

    public <TProjection> Enumerable<TSource> distinctBy(Function<TSource, TProjection> projection){
        return enumerableWithIterator(source ->
                new DistinctIterator<>(source, projection));
    }

    public List<TSource> toList(){
        return Evaluators.toList(this);
    }

    public HashMap<TSource, TSource> toDictionary(){
        return Evaluators.toDictionary(this, i -> i);
    }

    public <TKey> HashMap<TKey, TSource> toDictionary(Function<TSource, TKey> projection){
        return Evaluators.toDictionary(this, projection);
    }

    public <TKey> HashMap<TSource, List<TSource>> toGroupedDictionary(){
        return Evaluators.toGroupedDictionary(this, i -> i);
    }

    public String foldToString(){
        return unsafeIterEval(new FoldIterator<TSource, String>(this, (acc, elem) -> acc.toString() + elem.toString(), ""));
    }

    public <TKey> HashMap<TKey,  List<TSource>> toGroupedDictionary(Function<TSource, TKey> projection){
        return Evaluators.toGroupedDictionary(this, projection);
    }

    /**
     * Iterator methods
     */

    @Override
    public Iterator<TSource> iterator() {
        return iteratorGenerator.apply(this);
    }


    private <TAcc> TAcc evalUnsafeMapIterator(Iterator<TAcc> iterator) {
        iterator.hasNext();

        return iterator.next();
    }

    private static <TSource> TSource unsafeIterEval(Iterator<TSource> iterator) {
        iterator.hasNext();

        return iterator.next();
    }
}

