package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueADT {
    private int head;
    private int tail;
    private Object[] elements;

    public ArrayQueueADT() {
        elements = new Object[5];
        head = 0;
        tail = 0;
    }

    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, size(queue) + 1);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
    }

    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity == queue.elements.length) {
            Object[] newElements = new Object[2 * capacity];
            for (int i = 0; i < capacity - 1; i++) {
                newElements[i] = queue.elements[(queue.head + i) % capacity];
            }
            queue.elements = newElements;
            queue.head = 0;
            queue.tail = capacity - 1;
        }
    }

    public static Object dequeue(ArrayQueueADT queue) {
        assert size(queue) > 0;

        Object result = queue.elements[queue.head];
        queue.head = (queue.head + 1) % queue.elements.length;
        return result;
    }

    public static Object element(ArrayQueueADT queue) {
        assert size(queue) > 0;

        return queue.elements[queue.head];
    }

    public static int size(ArrayQueueADT queue) {
        return (queue.head <= queue.tail ? queue.tail - queue.head : queue.elements.length - queue.head + queue.tail);
    }

    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.head == queue.tail;
    }

    public static void clear(ArrayQueueADT queue) {
        queue.tail = queue.head;
    }
}
