package com.devshorts.enumerable.iterators;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class GroupRunsIterator<TSource> extends MapIterator<TSource, List<TSource>> {

    private TSource current;
    private TSource last;
    private List<TSource> neighbors = new LinkedList<>();
    ;
    private Boolean reset = false;

    public GroupRunsIterator(Iterable input) {
        super(input);
    }

    @Override
    public boolean hasNext(){
        if(!src().hasNext()){
            if(current != last){
                neighbors.add(current);

                last = current;

                return true;
            }
            else{
                return false;
            }
        }

        if(current == null && src().hasNext()){
            current = src().next();
        }

        neighbors.add(current);

        while(src().hasNext()){
            TSource next = src().next();

            last = current;

            if(current == next){
                neighbors.add(next);
            }
            else{
                current = next;
                break;
            }
        }

        return neighbors.size() > 0;
    }

    @Override
    public List<TSource> next(){
        List<TSource> ret = new LinkedList<>(neighbors);

        neighbors = new LinkedList<>();

        return ret;
    }

    private Iterator<TSource> src(){
        return (Iterator<TSource>)source;
    }
}
