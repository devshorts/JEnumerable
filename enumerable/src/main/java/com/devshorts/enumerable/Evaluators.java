package com.devshorts.enumerable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Evaluators {
    public static <TSource> List<TSource> toList(Enumerable<TSource> enumerable){
        List<TSource> r = new ArrayList<>();

        for(TSource item : enumerable){
            r.add(item);
        }

        return r;
    }

    public static <TKey, TSource> HashMap<TKey, TSource> toDictionary(Enumerable<TSource> enumerable,
                                                                         Function<TSource, TKey> projection) {
        HashMap<TKey, TSource> dict = new HashMap<>();

        for(TSource item : enumerable){
            dict.put(projection.apply(item), item);
        }

        return dict;
    }

    public static <TKey, TSource> HashMap<TKey, List<TSource>> toGroupedDictionary(Enumerable<TSource> enumerable, Function<TSource, TKey> projection) {
        HashMap<TKey, List<TSource>> dict = new HashMap<>();

        for(TSource item : enumerable){
            TKey key = projection.apply(item);

            if(dict.containsKey(key)){
                dict.get(key).add(item);
            }
            else{
                dict.put(key, new LinkedList<>());
                dict.get(key).add(item);
            }
        }

        return dict;
    }
}
