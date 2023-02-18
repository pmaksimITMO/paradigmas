package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueue {
    private int head = 0;
    private int tail = 0;
    private Object[] elements = new Object[5];

    public void enqueue(Object element) {
        Objects.requireNonNull(element);

        this.ensureCapacity(this.size() + 1);
        this.elements[this.tail] = element;
        this.tail = (this.tail + 1) % this.elements.length;
    }

    private void ensureCapacity(int capacity) {
        if (capacity == this.elements.length) {
            Object[] newElements = new Object[2 * capacity];
            for (int i = 0; i < capacity - 1; i++) {
                newElements[i] = this.elements[(this.head + i) % capacity];
            }
            this.elements = newElements;
            this.head = 0;
            this.tail = capacity - 1;
        }
    }

    public Object dequeue() {
        assert this.size() > 0;

        Object result = this.elements[this.head];
        this.head = (this.head + 1) % this.elements.length;
        return result;
    }

    public Object element() {
        assert this.size() > 0;

        return this.elements[this.head];
    }

    public int size() {
        return (this.head <= this.tail ? this.tail - this.head : this.elements.length - this.head + this.tail);
    }

    public boolean isEmpty() {
        return this.head == this.tail;
    }

    public void clear() {
        this.tail = this.head;
    }
}
