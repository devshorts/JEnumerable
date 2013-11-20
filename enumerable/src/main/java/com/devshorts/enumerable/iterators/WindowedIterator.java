package com.devshorts.enumerable.iterators;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class WindowedIterator<TSource> extends MapIterator<TSource, List<TSource>> {
    private final int windowSize;
    private boolean windowSeeded;
    List<TSource> queue = new LinkedList<>();
    List<TSource> next = new LinkedList<>();

    public WindowedIterator(Iterable<TSource> input, int windowSize) {
        super(input);
        this.windowSize = windowSize;
    }


    @Override
    public boolean hasNext(){
        if(!windowSeeded){
            seedWindow();

            windowSeeded = true;
        }

        return next.size() > 0;
    }

    @Override
    public List<TSource> next(){
        queue = new LinkedList<>(next);

        nextWindow();

        if(next.size() != windowSize){
            next.clear();
        }

        return (List<TSource>)(queue);
    }

    private void nextWindow(){
        next.remove(0);

        if(it().hasNext()){
            next.add(it().next());
        }
    }

    private void seedWindow(){
        int window = windowSize;
        while(it().hasNext() && window > 0){
            next.add(it().next());
            window--;
        }
    }

    private Iterator<TSource> it(){
        return (Iterator<TSource>)(source);
    }

}
