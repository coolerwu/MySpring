package vip.wulang.spring.collection.impl;

import vip.wulang.spring.exception.ArrayIndexOutOfBoundsException;

/**
 * @author coolerwu
 * @version 1.0
 */
public class MyArrayList<AnyType> {
    private static final int DEFAULT_CAPACITY = 16;
    private AnyType[] elements;
    private int capacity;
    private int currentIndex;

    public MyArrayList() {
        ensureCapacity(DEFAULT_CAPACITY);
    }

    private void ensureCapacity(int newCapacity) {
        if (newCapacity < capacity) {
            return;
        }
        newCapacity = twoTimes(newCapacity);
        @SuppressWarnings("unchecked")
        AnyType[] newElements = (AnyType[]) new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }

    private int twoTimes(int integer) {
        integer = integer | (integer >> 1);
        integer = integer | (integer >> 2);
        integer = integer | (integer >> 4);
        integer = integer | (integer >> 8);
        integer = integer | (integer >> 16);
        return integer + 1;
    }

    public AnyType get(int index) {
        checkIndex(index);
        return elements[index];
    }

    private void checkIndex(int index) {
        if (index > capacity || index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
    }

    public AnyType set(int index, AnyType element) {
        checkIndex(index);
        AnyType oldElement = elements[index];
        elements[index] = element;
        currentIndex = index;
        return oldElement;
    }

    public int size() {
        return currentIndex;
    }

    public boolean isEmpty() {
        return currentIndex == 0;
    }

    public boolean add(AnyType element) {
        checkCapacityEqualCurrentIndex();
        elements[currentIndex] = element;
        currentIndex++;
        return true;
    }

    private void checkCapacityEqualCurrentIndex() {
        if (capacity == currentIndex + 1) {
            int newCapacity = capacity + 1;
            ensureCapacity(newCapacity);
        }
    }

    public boolean remove(AnyType element) {
        requireNonNull(element);
        if (!contain(element)) {
            return false;
        }
        for (int i = 0; i < elements.length; i++) {
            AnyType data = elements[i];
            if (data.equals(element)) {
                checkCapacityEqualCurrentIndex();
                if (currentIndex - i + 1>= 0) {
                    System.arraycopy(elements, i + 1, elements, i, currentIndex - i + 1);
                }
                return true;
            }
        }
        return false;
    }

    public boolean contain(AnyType element) {
        requireNonNull(element);
        for (AnyType data : elements) {
            if (data.equals(element)) {
                return true;
            }
        }
        return false;
    }

    private void requireNonNull(Object obj) {
        if (obj == null)
            throw new NullPointerException();
    }
}
