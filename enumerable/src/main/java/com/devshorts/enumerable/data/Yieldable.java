package com.devshorts.enumerable.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

enum YieldType{
    YIELD,
    BANG,
    BREAK
}

public class Yieldable<T> {
    Action sideEffect;

    public static <T> Yieldable<T> yield(T element, Action sideEffect){
        return new Yieldable<>(YieldType.YIELD, initSingleList(element), sideEffect);
    }

    public static <T> Yieldable<T> yield(T element){
        return new Yieldable<>(YieldType.YIELD, initSingleList(element), null);
    }

    public static <T> Yieldable<T> bang(Iterable<T> elements, Action sideEffect){
        return new Yieldable<>(YieldType.BANG, elements, sideEffect);
    }

    public static <T> Yieldable<T> bang(Iterable<T> elements){
        return new Yieldable<>(YieldType.BANG, elements, null);
    }

    public static <T> Yieldable<T> yieldBreak(){
        return new Yieldable<>(YieldType.BREAK, null, null);
    }

    private static <T> List<T> initSingleList(T element){
        List<T> elements = new LinkedList<>();

        elements.add(element);

        return elements;
    }

    private YieldType yieldType;
    private Iterable<T> elements;
    private Iterator<T> currentIt;

    private Yieldable(YieldType type, Iterable<T> elements, Action sideEffect){
        yieldType = type;

        this.elements = elements;

        this.sideEffect = sideEffect;
    }

    public T value(){

        T e = currentIt.next();

        if(sideEffect != null && !currentIt.hasNext()){
            sideEffect.exec();
        }

        return e;
    }

    public boolean hasNext(){
        if(currentIt == null){
            currentIt = elements.iterator();
        }

        return isYield() && currentIt.hasNext();
    }

    public boolean isBang(){
        return yieldType == YieldType.BANG;
    }

    public boolean isYield(){
        return !isBreak();
    }

    public boolean isBreak(){
        return yieldType == YieldType.BREAK;
    }

    public boolean shouldGen() {
        return yieldType == YieldType.YIELD || isBang() && !currentIt.hasNext();
    }
}
