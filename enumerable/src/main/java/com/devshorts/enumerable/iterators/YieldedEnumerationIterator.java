package com.devshorts.enumerable.iterators;

import com.devshorts.enumerable.data.Yieldable;

import java.util.Iterator;
import java.util.function.Supplier;

public class YieldedEnumerationIterator<TSource> implements Iterator<TSource> {
    private Supplier<Yieldable<TSource>> generator;

    public YieldedEnumerationIterator(Supplier<Yieldable<TSource>> generator) {
        this.generator = generator;
    }

    Yieldable<TSource> nextElem;

    @Override
    public boolean hasNext() {
        if(nextElem == null || nextElem.shouldGen()){
            nextElem = generator.get();
        }

        return nextElem.isYield() && nextElem.hasNext();
    }

    @Override
    public TSource next() {
        return nextElem.value();
    }
}
