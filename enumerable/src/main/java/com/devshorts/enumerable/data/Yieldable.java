package com.devshorts.enumerable.data;

enum YieldType{
    YIELD,
    BREAK
}
public class Yieldable<T> {
    Action sideEffect;

    public static <T> Yieldable<T> yield(T element, Action sideEffect){
        return new Yieldable<>(YieldType.YIELD, element, sideEffect);
    }

    public static <T> Yieldable<T> yield(T element){
        return new Yieldable<>(YieldType.YIELD, element, null);
    }

    public static <T> Yieldable<T> yieldBreak(){
        return new Yieldable<>(YieldType.BREAK, null, null);
    }

    private Yieldable(YieldType type, T element, Action sideEffect){
        yieldType = type;
        this.element = element;
        this.sideEffect = sideEffect;
    }

    private YieldType yieldType;
    private T element;

    public T value(){
        if(sideEffect != null){
            sideEffect.exec();
        }
        return element;
    }

    public boolean isYield(){
        return yieldType == YieldType.YIELD;
    }

    public boolean isBreak(){
        return yieldType == YieldType.BREAK;
    }
}
