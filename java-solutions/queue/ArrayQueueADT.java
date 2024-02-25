package queue;

import java.util.Objects;

public class ArrayQueueADT {
    private int head;
    private int tail;
    private Object[] elements;

    public ArrayQueueADT() {

    }

    public static ArrayQueueADT create() {
        ArrayQueueADT queue = new ArrayQueueADT();
        queue.elements = new Object[5];
        return queue;
    }

    // let save(queue, head, tail, st):
    // (forall i = head..tail-1) queue.elements`[(st + i) % a`.length] = queue.elements[(st + i) % a.length]

    // Pre: element != null
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a + 1
    // save(queue, head, tail, head`)
    // queue.elements`[tail`] = element
    public static void enqueue(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);
        ensureCapacity(queue, size(queue) + 1);
        queue.elements[queue.tail] = element;
        queue.tail = (queue.tail + 1) % queue.elements.length;
    }

    // Pre: element != null
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a + 1
    // save(head, tail, head`)
    // queue.elements`[head`] = element
    public static void push(ArrayQueueADT queue, Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(queue, size(queue) + 1);
        queue.head = (queue.head - 1 + queue.elements.length) % queue.elements.length;
        queue.elements[queue.head] = element;
    }

    // Pre: element != null && queue.size() > position >= 0
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a
    // saveExceptOne(head, tail, head`, position, element)
    // queue.elements`[head`] = element
    public static void set(ArrayQueueADT queue, int position, Object element) {
        Objects.requireNonNull(element);
        assert size(queue) > position && position >= 0;

        int realPosition = (queue.head + position) % queue.elements.length;
        queue.elements[realPosition] = element;
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
    // a` = a - 1
    // save(queue, head + 1, tail, head`)
    // R = queue.elements[head]
    public static Object dequeue(ArrayQueueADT queue) {
        assert size(queue) > 0;

        Object result = queue.elements[queue.head];
        queue.head = (queue.head + 1) % queue.elements.length;
        return result;
    }

    // Pre: a > 0
    // Post: Пусть a - число элементов в очереди до операции, а a` - после
    // a` = a - 1
    // save(head + 1, tail`, head`)
    // R = первый элемент в очереди
    public static Object remove(ArrayQueueADT queue) {
        assert size(queue) > 0;

        queue.tail = (queue.tail - 1 + queue.elements.length) % queue.elements.length;
        return queue.elements[queue.tail];
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post: a` = a
    // queue.elements` = queue.elements
    // R = queue.elements[head]
    public static Object element(ArrayQueueADT queue) {
        assert size(queue) > 0;

        return queue.elements[queue.head];
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post: a` = a
    // elements` = elements
    // R = последний элемент в очереди
    public static Object peek(ArrayQueueADT queue) {
        assert size(queue) > 0;

        return queue.elements[(queue.tail - 1 + queue.elements.length) % queue.elements.length];
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0 && queue.size() > index >= 0
    // Post: a` = a
    // queue.elements` = queue.elements
    // R = элемент c номером index считая от головы
    public static Object get(ArrayQueueADT queue, int index) {
        assert size(queue) > index && index >= 0;

        return queue.elements[(queue.head + index + queue.elements.length) % queue.elements.length];
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
    // Post: a` = 0
    public static void clear(ArrayQueueADT queue) {
        queue.tail = queue.head;
    }
}
