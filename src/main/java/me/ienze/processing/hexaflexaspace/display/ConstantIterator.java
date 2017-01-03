package me.ienze.hexaflexaspace.display;

import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Ienze
 */
public class ConstantIterator<T> implements Iterator<T> {

    private List<T> objects;
    private int i=0;

    public ConstantIterator(T object) {
        this.objects = new ArrayList<T>();
        this.objects.add(object);
    }

    public ConstantIterator(List<T> objects) {
        this.objects = objects;
    }

    @Override
    public boolean hasNext() {
        return objects.size() > 0;
    }

    @Override
    public T next() {
        T object = objects.get(i);
        i = (i+1) % objects.size();
        return object;
    }
}
