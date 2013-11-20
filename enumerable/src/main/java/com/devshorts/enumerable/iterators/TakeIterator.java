package com.devshorts.enumerable.iterators;

public class TakeIterator<TSource> extends EnumerableIterator<TSource> {
    private int takeNum;

    public TakeIterator(Iterable<TSource> results, int n) {
        super(results);
        takeNum = n;
    }

    @Override
    public boolean hasNext() {
        return source.hasNext() && takeNum > 0;
    }

    @Override
    public TSource next(){
        takeNum--;
        return source.next();
    }
}
