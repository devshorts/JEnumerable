package com.devshorts.enumerable.data;

public class Box<T>{
    public T elem;

    public Box(T elem){
        this.elem = elem;
    }

    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof Box)){
            return false;
        }

        return elem.equals(((Box) o).elem);
    }
}

