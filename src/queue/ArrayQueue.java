package queue;

import java.util.Objects;

public class ArrayQueue {
    private int head = 0;
    private int tail = 0;
    private Object[] elements = new Object[5];

    // let save(head, tail, st):
    // (forall i = head..tail-1) this.elements`[(st + i) % a`.length] = this.elements[(st + i) % a.length]

    // Pre: element != null
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a + 1
    // save(head, tail, head`)
    // this.elements`[tail`] = element

    public void enqueue(Object element) {
        Objects.requireNonNull(element);

        this.ensureCapacity(this.size() + 1);
        this.elements[this.tail] = element;
        this.tail = (this.tail + 1) % this.elements.length;
    }

    // Pre: element != null
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a + 1
    // save(head, tail, head`)
    // elements`[head`] = element
    public void push(Object element) {
        Objects.requireNonNull(element);

        ensureCapacity(size() + 1);
        this.head = (this.head - 1 + this.elements.length) % this.elements.length;
        this.elements[this.head] = element;
    }

    // Pre: element != null && size() > position >= 0
    // Post: Если a - число элементов в очереди до операции, а a` - после, то:
    // a` = a
    // saveExceptOne(head, tail, head`, position, element)
    // elements`[head`] = element
    public void set(int position, Object element) {
        Objects.requireNonNull(element);
        assert this.size() > position && position >= 0;

        int realPosition = (this.head + position) % this.elements.length;
        this.elements[realPosition] = element;
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
    // a` = a - 1
    // save(head + 1, tail`, head`)
    // R = первый элемент в очереди
    public Object dequeue() {
        assert this.size() > 0;

        Object result = this.elements[this.head];
        this.head = (this.head + 1) % this.elements.length;
        return result;
    }

    // Pre: a > 0
    // Post: Пусть a - число элементов в очереди до операции, а a` - после
    // a` = a - 1
    // save(head + 1, tail`, head`)
    // R = первый элемент в очереди
    public Object remove() {
        assert this.size() > 0;

        this.tail = (this.tail - 1 + this.elements.length) % this.elements.length;
        return this.elements[this.tail];
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post: a` = a
    // this.elements` = this.elements
    // R = первый элемент в очереди
    public Object element() {
        assert this.size() > 0;

        return this.elements[this.head];
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0
    // Post: a` = a
    // elements` = elements
    // R = последний элемент в очереди
    public Object peek() {
        assert this.size() > 0;

        return this.elements[(this.tail - 1 + this.elements.length) % this.elements.length];
    }

    // Пусть a - число элементов в очереди до операции, а a` - после
    // Pre: a > 0 && this.size() > index >= 0
    // Post: a` = a
    // this.elements` = this.elements
    // R = элемент c номером index считая от головы
    public Object get(int index) {
        assert this.size() > index && index >= 0;

        return this.elements[(this.head + index + this.elements.length) % this.elements.length];
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
