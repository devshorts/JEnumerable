package com.devshorts.enumerable.iterators;

import com.devshorts.enumerable.Enumerable;
import com.devshorts.enumerable.data.ProjectionPair;

import java.util.*;
import java.util.function.Function;

public class OrderByIterator<TSource, TProjection> extends EnumerableIterator<TSource> {


    private ProjectionPair[] buffer;
    private Function<TSource, TProjection> projection;
    private Comparator<TProjection> comparator;
    private Integer idx = 0;

    public OrderByIterator(Iterable<TSource> source,
                           Function<TSource, TProjection> projection,
                           Comparator<TProjection> comparator) {
        super(source);

        this.projection = projection;
        this.comparator = comparator;

        sort();
    }

    @Override
    public boolean hasNext(){
        return idx < buffer.length;
    }

    @Override
    public TSource next(){
        TSource value = (TSource)buffer[idx].value;
        idx++;
        return value;
    }

    private void sort(){
        idx = 0;

        buffer = Enumerable.init(evaluateEnumerable())
                .map(value -> new ProjectionPair(projection.apply(value), value, new Comparator<ProjectionPair<TProjection, ?>>() {
                    @Override
                    public int compare(ProjectionPair<TProjection, ?> o1, ProjectionPair<TProjection, ?> o2) {
                        return comparator.compare(o1.projection, o2.projection);
                    }
                }))
                .toList()
                .toArray(new ProjectionPair[0]);

        Arrays.parallelSort(buffer);
    }

    private List<TSource> evaluateEnumerable(){
        List<TSource> r = new ArrayList<>();
        while(super.hasNext()){
            r.add(super.next());
        }
        return r;
    }

}
