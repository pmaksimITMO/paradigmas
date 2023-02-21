package queue;

import java.util.Objects;

public class ArrayQueueADT {
    private int head;
    private int tail;
    private Object[] elements;

    public ArrayQueueADT() {

    }

    // let save(queue, head, tail, st):
    // (forall i = head..tail-1) queue.elements`[(st + i) % a`.length] = queue.elements[(st + i) % a.length]

    // Pre: element != null
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a + 1, tail` = tail + 1
    // save(queue, head, tail, head`)
    // queue.elements`[tail`] = element
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, size(queue) + 1);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
    }

    // Pre: true
    // Post: Пусть a - число элементов в очереди до операции, а a` - после
    // a` = 1 if a is null else (a` = a if a < capacity else a` = 2 * a)
    // save(queue, head, tail, 0)
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (queue.elements == null) {
            queue.elements = new Object[1];
        }
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

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post:
    // a` = a - 1, tail` = tail, head` = head + 1
    // save(queue, head + 1, tail, head`)
    // R = queue.elements[head]
    public static Object dequeue(ArrayQueueADT queue) {
        assert size(queue) > 0;

        Object result = queue.elements[queue.head];
        queue.head = (queue.head + 1) % queue.elements.length;
        return result;
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post: a` = a, head` = head, tail` = tail
    // queue.elements` = queue.elements
    // R = queue.elements[head]
    public static Object element(ArrayQueueADT queue) {
        assert size(queue) > 0;

        return queue.elements[queue.head];
    }

    // Если a - число элементов в очереди
    // Pre: true
    // Post: R = a
    public static int size(ArrayQueueADT queue) {
        return (queue.head <= queue.tail ? queue.tail - queue.head : queue.elements.length - queue.head + queue.tail);
    }

    // Если a - число элементов в очереди
    // Pre: true
    // Post: R = (a == 0)
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.head == queue.tail;
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: true
    // Post: a` = 0, head` = 0, tail` = 0
    public static void clear(ArrayQueueADT queue) {
        queue.tail = queue.head;
    }
}
