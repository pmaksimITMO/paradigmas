package queue;

import java.util.Arrays;
import java.util.Objects;

public class ArrayQueueModule {
    private static int head = 0;
    private static int tail = 0;
    private static Object[] elements = new Object[5];

    public static void enqueue(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(size() + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }

    private static void ensureCapacity(int capacity) {
        if (capacity == elements.length) {
            Object[] newElements = new Object[2 * capacity];
            for (int i = 0; i < capacity - 1; i++) {
                newElements[i] = elements[(head + i) % capacity];
            }
            elements = newElements;
            head = 0;
            tail = capacity - 1;
        }
    }

    public static Object dequeue() {
        assert size() > 0;

        Object result = elements[head];
        head = (head + 1) % elements.length;
        return result;
    }

    public static Object element() {
        assert size() > 0;

        return elements[head];
    }

    public static int size() {
        return (head <= tail ? tail - head : elements.length - head + tail);
    }

    public static boolean isEmpty() {
        return head == tail;
    }

    public static void clear() {
        tail = head;
    }
}
