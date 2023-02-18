package queue;

import java.util.Objects;

public class ArrayQueueModule {
    private static int head, tail;
    private static Object[] elements = new Object[5];

    public static void enqueue(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(size() + 1);
        tail = (tail + 1) % elements.length;
        elements[tail] = element;
    }

    private static void ensureCapacity(int capacity) {
        if (capacity == elements.length) {
            Object[] newElements = new Object[2 * capacity];
            for (int i = 0; i < capacity; i++) {
                newElements[i] = elements[(head + i) % capacity];
            }
            elements = newElements;
            head = 0;
            tail = capacity - 1;
        }
    }

    public static Object dequeue() {
        assert size() > 0;
        Object result = elements[tail];
        tail = (tail - 1 + elements.length) % elements.length;
        return result;
    }

    public static Object element() {
        assert size() > 0;

        return elements[tail];
    }

    public static int size() {
        return (tail < head ? head - tail : elements.length - head + tail) + 1;
    }

    public static boolean isEmpty() {
        return head == tail;
    }

    public static void clear() {
        tail = head;
    }
}
