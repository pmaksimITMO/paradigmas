package queue;

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
            Object[] newElements = new Object[2 * capacity];
            for (int i = 0; i < capacity - 1; i++) {
                newElements[i] = this.elements[(head + i) % capacity];
            }
            elements = newElements;
            head = 0;
            tail = capacity - 1;
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

    protected void dropNthImpl(int n) {
        int position = head, id = 0, deleted = 0;
        while (position != tail) {
            if (id % n == n - 1) {
                deleted++;
            } else {
                elements[decrement(position, deleted)] = elements[position];
            }
            id++;
            position = increment(position, 1);
        }
    }

    private int increment(int val, int i) {
        return (val + i) % elements.length;
    }

    private int decrement(int val, int i) {
        return (val - i + elements.length) % elements.length;
    }
}
