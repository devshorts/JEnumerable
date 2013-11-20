package com.devshorts.enumerable.iterators;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TailsIterator<TSource> extends MapIterator<TSource, List<TSource>> {

    private List<TSource> cache;

    public TailsIterator(Iterable input) {
        super(input);
    }

    @Override
    public boolean hasNext(){
        if(cache == null){
            cache = new LinkedList<>();
            while(source.hasNext()){
                cache.add((TSource)source.next());
            }
        }

        return cache.size() > 0;
    }

    @Override
    public List<TSource> next(){
        List<TSource> tails = new LinkedList<>(cache);

        cache.remove(0);

        return tails;
    }
}
