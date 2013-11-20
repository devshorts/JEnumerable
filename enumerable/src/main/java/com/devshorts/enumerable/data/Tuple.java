package com.devshorts.enumerable.data;

public class Tuple<T1, T2> {

    public final T1 item1;
    public final T2 item2;

    public Tuple(T1 item1, T2 item2){

        this.item1 = item1;
        this.item2 = item2;
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof Tuple)){
            return false;
        }

        Tuple<T1, T2> otherTupe = (Tuple<T1, T2>)other;

        return item1.equals(otherTupe.item1) && item2.equals(otherTupe.item2);
    }
    @Override
    public int hashCode() {
        int hashCode = 0;

        hashCode = hashCode * 37 + this.item1.hashCode();
        hashCode = hashCode * 37 + this.item2.hashCode();

        return hashCode;
    }
}
