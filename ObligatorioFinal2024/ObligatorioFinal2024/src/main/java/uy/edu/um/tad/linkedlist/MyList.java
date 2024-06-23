package uy.edu.um.tad.linkedlist;

import java.util.Comparator;
import java.util.Iterator;

public interface MyList<T> extends Iterable<T>{

    void add(T value);

    T get(int position);

    boolean contains(T value);

    void remove(T value);

    int size();

    Node<T> getFirst();

    boolean isEmpty();

    void sort(Comparator<? super T> c);

    void addD(int index, T element);
}
