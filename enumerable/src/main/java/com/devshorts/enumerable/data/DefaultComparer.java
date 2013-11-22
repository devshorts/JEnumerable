package com.devshorts.enumerable.data;

import java.util.Comparator;

public class DefaultComparer implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        return ((Comparable)(o1)).compareTo(o2);
    }
}
