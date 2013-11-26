package com.devshorts.enumerable.iterators;

import java.util.ArrayList;
import java.util.List;

public class ListIterator<TSource> extends EnumerableIterator<TSource>{

    protected List<TSource> input;

    public ListIterator(List<TSource> input) {
        super(input);
        this.input = input;
    }
}
