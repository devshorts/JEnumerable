package com.devshorts.enumerable.iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnumerableIterator<TSource> implements Iterator<TSource> {
    protected Iterator<TSource> source;
    private Iterable<TSource> input;

    public EnumerableIterator(Iterable<TSource> input){
        this.input = input;

        reset();
    }

    protected void reset(){
        source = input.iterator();
    }

    @Override
    public boolean hasNext() {
        return source.hasNext();
    }

    @Override
    public TSource next() {
        return source.next();
    }

    protected boolean indexable(){
        if(source instanceof ListIterator){
            ListIterator<TSource> i = (ListIterator<TSource>)source;

            return i.input != null;
        }

        return false;
    }

    protected int length(){
        if(indexable()){
            return arrayList().size();
        }

        return -1;
    }

    protected TSource index(int n){
        return arrayList().get(n);
    }

    private List<TSource> arrayList(){
        if(indexable()){
            ListIterator<TSource> i = (ListIterator<TSource>)source;

            return i.input;
        }

        return null;
    }
}
