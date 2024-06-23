package uy.edu.um.tad.heap;

import lombok.Getter;
import uy.edu.um.tad.linkedlist.MyLinkedListImpl;
import uy.edu.um.tad.linkedlist.MyList;

import java.util.Comparator;
import java.util.NoSuchElementException;

public class MyHeap<T extends Comparable<T>> {

    private T[] heapArray;
    @Getter
    private int size;
    private int capacity;
    private Comparator<T> comparator;

    public MyHeap(int capacity, Comparator<T> comparator) {
        this.heapArray = (T[]) new Comparable[capacity];
        this.size = 0;
        this.capacity = capacity;
        this.comparator = comparator;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    public void insert(T element) {
        if (size >= capacity) {
            System.out.println("HeapLleno");
            return;
        }
        heapArray[size] = element;
        heapUp(size);
        size++;
    }

    T get() {
        if (size == 0) {
            System.out.println("Vacío");
            return null;
        }
        T top = heapArray[0];
        heapArray[0] = heapArray[size - 1];
        size--;
        heapDown(0);
        return top;
    }

    private void heapDown(int index) {
        int leftChildIndex, rightChildIndex, largerChildIndex;

        while (index < size) {
            leftChildIndex = 2 * index + 1;
            rightChildIndex = 2 * index + 2;
            largerChildIndex = index;

            if (leftChildIndex < size && comparator.compare(heapArray[leftChildIndex], heapArray[largerChildIndex]) < 0) {
                largerChildIndex = leftChildIndex;
            }
            if (rightChildIndex < size && comparator.compare(heapArray[rightChildIndex], heapArray[largerChildIndex]) < 0) {
                largerChildIndex = rightChildIndex;
            }
            if (largerChildIndex == index) {
                break;
            }
            swap(index, largerChildIndex);
            index = largerChildIndex;
        }
    }

    private void heapUp(int index) {
        int parentIndex = parent(index);
        while (index > 0 && comparator.compare(heapArray[index], heapArray[parentIndex]) < 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = parent(index);
        }
    }

    private void swap(int i, int j) {
        T temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
    }

    public MyList<T> extractAllSorted() {
        MyList<T> sortedList = new MyLinkedListImpl<>();
        while (size > 0) {
            sortedList.add(get());
        }
        return sortedList;
    }

    public T extractMin() {
        if (size == 0) {
            throw new NoSuchElementException("Heap is empty");
        }
        T min = heapArray[0];
        heapArray[0] = heapArray[size - 1];
        size--;
        heapDown(0);
        return min;
    }
}