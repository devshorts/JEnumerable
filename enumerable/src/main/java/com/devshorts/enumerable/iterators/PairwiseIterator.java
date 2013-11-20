package com.devshorts.enumerable.iterators;

import com.devshorts.enumerable.data.Tuple;

public class PairwiseIterator<TSource> extends MapIterator<TSource, Tuple<TSource, TSource>> {

    private TSource first;
    private TSource second;

    public PairwiseIterator(Iterable input) {
        super(input);
    }


    @Override
    public boolean hasNext(){
        if(!source.hasNext()){
            return false;
        }

        if(first == null){
            first = (TSource)source.next();
        }

        if(second == null && source.hasNext()){
            second = (TSource)source.next();

            return true;
        }

        return source.hasNext();
    }

    @Override
    public Tuple<TSource, TSource> next(){
        Tuple<TSource, TSource> tuple = new Tuple<>(first, second);

        first = second;
        second = null;

        return tuple;
    }
}
