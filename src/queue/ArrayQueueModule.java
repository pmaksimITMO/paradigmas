package queue;

import java.util.Objects;

public class ArrayQueueModule {
    private static int head = 0;
    private static int tail = 0;
    private static Object[] elements = new Object[5];

    // let save(head, tail, st):
    // (forall i = head..tail-1) elements`[(st + i) % a`.length] = elements[(st + i) % a.length]

    // Pre: element != null
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a + 1, tail` = tail + 1
    // save(head, tail, head`)
    // elements`[tail`] = element
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(size() + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }

    // Pre: true
    // Post: Пусть a - число элементов в очереди до операции, а a` - после
    // a` = a if capacity < a else a` = 2 * a
    // save(head, tail, 0)
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

    // Pre: a > 0
    // Post: Пусть a - число элементов в очереди до операции, а a` - после
    // a` = a - 1, tail` = tail - 1, head` = head
    // save(head + 1, tail`, head`)
    // R = elements[tail - 1]
    public static Object dequeue() {
        assert size() > 0;

        Object result = elements[head];
        head = (head + 1) % elements.length;
        return result;
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post: a` = a, head` = head, tail` = tail
    // elements` = elements
    // R = elements[head]
    public static Object element() {
        assert size() > 0;

        return elements[head];
    }

    // Если a - число элементов в очереди
    // Pre: true
    // Post: R = a
    public static int size() {
        return (head <= tail ? tail - head : elements.length - head + tail);
    }

    // Если a - число элементов в очереди
    // Pre: true
    // Post: R = (a == 0)
    public static boolean isEmpty() {
        return head == tail;
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: true
    // Post: a` = 0, head` = 0, tail` = 0
    public static void clear() {
        tail = head;
    }
}
