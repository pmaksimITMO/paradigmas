package queue;

import java.util.Objects;

public class ArrayQueueModule {
    private static int head = 0;
    private static int tail = 0;
    private static Object[] elements = new Object[5];

    // let save(head, tail, st):
    // (forall i = head..tail-1) elements`[(st + i) % elements`.length] = elements[(st + i) % elements.length]

    // let saveExceptOne(head, tail, st, id, obj):
    // сначала (forall i = head..tail-1) elements`[(st + i) % a`.length] = elements[(st + i) % a.length]
    // потом elements`[(head + id - 1) % elements`.length] = obj

    // Pre: element != null
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a + 1
    // save(head, tail, head`)
    // elements`[tail`] = element
    public static void enqueue(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(size() + 1);
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
    }

    // Pre: element != null
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a + 1
    // save(head, tail, head`)
    // elements`[head`] = element
    public static void push(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(size() + 1);
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
    }

    // Pre: element != null && size() > position >= 0
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a
    // saveExceptOne(head, tail, head`, position, element)
    // elements`[head`] = element
    public static void set(int position, Object element) {
        Objects.requireNonNull(element);
        assert size() > position && position >= 0;

        int realPosition = (head + position) % elements.length;
        elements[realPosition] = element;
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
    // a` = a - 1
    // save(head + 1, tail`, head`)
    // R = первый элемент в очереди
    public static Object dequeue() {
        assert size() > 0;

        Object result = elements[head];
        head = (head + 1) % elements.length;
        return result;
    }

    // Pre: a > 0
    // Post: Пусть a - число элементов в очереди до операции, а a` - после
    // a` = a - 1
    // save(head + 1, tail`, head`)
    // R = первый элемент в очереди
    public static Object remove() {
        assert size() > 0;

        tail = (tail - 1 + elements.length) % elements.length;
        return elements[tail];
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post: a` = a
    // elements` = elements
    // R = первый элемент в очереди
    public static Object element() {
        assert size() > 0;

        return elements[head];
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post: a` = a
    // elements` = elements
    // R = последний элемент в очереди
    public static Object peek() {
        assert size() > 0;

        return elements[(tail - 1 + elements.length) % elements.length];
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0 && size() > index >= 0
    // Post: a` = a
    // elements` = elements
    // R = элемент c номером index считая от головы
    public static Object get(int index) {
        assert size() > index && index >= 0;

        return elements[(head + index + elements.length) % elements.length];
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
    // Post: a` = 0
    public static void clear() {
        tail = head;
    }
}
