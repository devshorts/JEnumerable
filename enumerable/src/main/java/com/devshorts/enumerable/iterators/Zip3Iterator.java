package com.devshorts.enumerable.iterators;

import com.devshorts.enumerable.data.Zip3Func;

import java.util.Iterator;
import java.util.function.BiFunction;

public class Zip3Iterator<T1, T2, T3, T4> extends MapIterator<T1, T4> {

    private Iterator<T2> zipWith1Iterator;
    private Iterator<T3> zipWith2Iterator;
    private Zip3Func<T1, T2, T3, T4> zipper;

    public Zip3Iterator(Iterable<T1> input,
                       Iterable<T2> zipWith1,
                       Iterable<T3> zipWith2,
                       Zip3Func<T1, T2, T3, T4> zipper) {
        super(input);
        this.zipWith1Iterator = zipWith1.iterator();
        this.zipWith2Iterator = zipWith2.iterator();
        this.zipper = zipper;
    }

    @Override
    public boolean hasNext(){
        return source.hasNext() &&
                zipWith1Iterator.hasNext() &&
                zipWith2Iterator.hasNext();
    }

    @Override
    public T4 next(){
        T1 n1 = (T1)source.next();
        T2 n2 = zipWith1Iterator.next();
        T3 n3 = zipWith2Iterator.next();

        return zipper.apply(n1, n2, n3);
    }
}
