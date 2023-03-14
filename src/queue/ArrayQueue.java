package queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ArrayQueue extends AbstractQueue {
    private int head = 0;
    private int tail = 0;
    private Object[] elements = new Object[5];

    public ArrayQueue makeCopy() {
        ArrayQueue queue = new ArrayQueue();
        int tmp = head;
        while (tmp != tail) {
            queue.enqueue(elements[tmp]);
            tmp = increment(tmp, 1);
        }
        return queue;
    }

    protected void enqueueImpl(Object element) {
        ensureCapacity(size + 1);
        elements[tail] = element;
        tail = increment(tail, 1);
    }

    protected void pushImpl(Object element) {
        ensureCapacity(size + 1);
        head = decrement(head, 1);
        elements[head] = element;
    }

    protected void setImpl(int position, Object element) {
        elements[increment(head, position)] = element;
    }

    // Pre: true
    // Post: Пусть a - число элементов в очереди до операции, а a` - после
    // a` = 1 if a is null else (a` = a if a < capacity else a` = 2 * a)
    // Все элементы, лежавшие в очереди ранее, сохранены
    private void ensureCapacity(int capacity) {
        if (capacity == elements.length) {
            elements = Arrays.copyOf(elements, 2 * capacity);
            if (head > tail) {
                System.arraycopy(elements, 0, elements, capacity, tail);
                tail = head + capacity - 1;
            }
        }
    }

    protected void dequeueImpl() {
        head = increment(head, 1);
    }

    protected void removeImpl() {
        tail = decrement(tail, 1);
    }

    protected Object elementImpl() {
        return elements[head];
    }

    protected Object peekImpl() {
        return elements[decrement(tail, 1)];
    }

    protected Object getImpl(int index) {
        return elements[increment(head,index)];
    }

    protected void clearImpl() {
        tail = head;
    }

    private int increment(int val, int i) {
        return (val + i) % elements.length;
    }

    private int decrement(int val, int i) {
        return (val - i + elements.length) % elements.length;
    }
}
