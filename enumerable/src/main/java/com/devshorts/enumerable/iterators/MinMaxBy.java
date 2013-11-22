package com.devshorts.enumerable.iterators;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MinMaxBy<TSource, TProjection> extends EnumerableIterator<TSource> {


    private Function<TSource, TProjection> projector;
    private final BiFunction<TProjection, TProjection, Boolean> comparator;

    public MinMaxBy(Iterable<TSource> input,
                    Function<TSource, TProjection> projector,
                    BiFunction<TProjection, TProjection, Boolean> comparator) {
        super(input);
        this.projector = projector;
        this.comparator = comparator;
    }

    @Override
    public boolean hasNext(){
        return source.hasNext();
    }

    @Override
    public TSource next(){
        TSource current = null;
        while(source.hasNext()){
            if(current == null){
                current = source.next();
            }
            else {
                TSource next = source.next();

                if(comparator.apply(projector.apply(current), projector.apply(next))){
                    current = next;
                }
            }
        }

        return current;
    }
}
