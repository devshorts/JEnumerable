package com.devshorts.enumerable.data;

@FunctionalInterface
public interface Zip3Func<T1, T2, T3, T4> {
    T4 apply(T1 item1, T2 item2, T3 item3);
}
