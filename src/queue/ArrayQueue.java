package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueue {
    private int head = 0;
    private int tail = 0;
    private Object[] elements = new Object[5];

    // let save(head, tail, st):
    // (forall i = head..tail-1) this.elements`[(st + i) % a`.length] = this.elements[(st + i) % a.length]

    // Pre: element != null
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a + 1, tail` = tail + 1
    // save(head, tail, head`)
    // this.elements`[tail`] = element

    public void enqueue(Object element) {
        Objects.requireNonNull(element);

        this.ensureCapacity(this.size() + 1);
        this.elements[this.tail] = element;
        this.tail = (this.tail + 1) % this.elements.length;
    }

    // Pre: true
    // Post: Пусть a - число элементов в очереди до операции, а a` - после
    // a` = a if a < capacity else a` = 2 * a
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

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post:
    // a` = a - 1, tail` = tail, head` = head + 1
    // save(head + 1, tail`, head`)
    // R = this.elements[head]
    public Object dequeue() {
        assert this.size() > 0;

        Object result = this.elements[this.head];
        this.head = (this.head + 1) % this.elements.length;
        return result;
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post: a` = a, head` = head, tail` = tail
    // this.elements` = this.elements
    // R = this.elements[head]
    public Object element() {
        assert this.size() > 0;

        return this.elements[this.head];
    }

    // Если a - число элементов в очереди
    // Pre: true
    // Post: R = a
    public int size() {
        return (this.head <= this.tail ? this.tail - this.head : this.elements.length - this.head + this.tail);
    }

    // Если a - число элементов в очереди
    // Pre: true
    // Post: R = (a == 0)
    public boolean isEmpty() {
        return this.head == this.tail;
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: true
    // Post: a` = 0, head` = 0, tail` = 0
    public void clear() {
        this.tail = this.head;
    }
}
