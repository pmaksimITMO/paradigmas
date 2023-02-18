package queue;

import java.util.Objects;

public class ArrayQueueADT {
    private int head, tail;
    private Object[] elements;

    public ArrayQueueADT() {

    }

    public static ArrayQueueADT create() {
        ArrayQueueADT queue = new ArrayQueueADT();
        queue.elements = new Object[5];
        return queue;
    }

    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(queue, size(queue) + 1);
        queue.tail = (queue.tail + 1) % queue.elements.length;
        queue.elements[queue.tail] = element;
    }

    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity == queue.elements.length) {
            Object[] newElements = new Object[2 * capacity];
            for (int i = 0; i < capacity; i++) {
                newElements[i] = queue.elements[(queue.head + i) % capacity];
            }
            queue.elements = newElements;
            queue.head = 0;
            queue.tail = capacity - 1;
        }
    }

    public static Object dequeue(ArrayQueueADT queue) {
        assert size(queue) > 0;
        Object result = queue.elements[queue.tail];
        queue.tail = (queue.tail - 1 + queue.elements.length) % queue.elements.length;
        return result;
    }

    public static Object element(ArrayQueueADT queue) {
        assert size(queue) > 0;

        return queue.elements[queue.tail];
    }

    public static int size(ArrayQueueADT queue) {
        return (queue.tail < queue.head ? queue.head - queue.tail : queue.elements.length - queue.head + queue.tail) + 1;
    }

    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.head == queue.tail;
    }

    public static void clear(ArrayQueueADT queue) {
        queue.tail = queue.head;
    }
}
