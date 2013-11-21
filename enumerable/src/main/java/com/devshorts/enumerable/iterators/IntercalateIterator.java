package com.devshorts.enumerable.iterators;

import java.util.List;

public class IntercalateIterator<TSource> extends EnumerableIterator<TSource> {
    private List<TSource> intercalator;

    public IntercalateIterator(Iterable<TSource> input, List<TSource> intercalator) {
        super(input);
        this.intercalator = intercalator;
    }

    private boolean intercalate = false;
    private int idx = 0;

    @Override
    public boolean hasNext(){
        if(intercalate && source.hasNext()){
            return idx < intercalator.size();
        }

        return source.hasNext();
    }

    @Override
    public TSource next(){
        TSource n;
        if(intercalate){
            n = intercalator.get(idx);

            idx++;

            if(idx == intercalator.size()){
                intercalate = false;
            }
        }
        else{
            idx = 0;

            n = source.next();

            intercalate = true;
        }

        return n;
    }


}
