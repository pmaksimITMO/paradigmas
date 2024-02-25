package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size;

    public void enqueue(Object element) {
        Objects.requireNonNull(element);

        enqueueImpl(element);
        size++;
    }

    protected abstract void enqueueImpl(Object element);

    public void push(Object element) {
        Objects.requireNonNull(element);

        pushImpl(element);
        size++;
    }

    protected abstract void pushImpl(Object element);

    public void set(int position, Object element) {
        Objects.requireNonNull(element);
        assert 0 <= position && position < size;
        
        setImpl(position, element);
    }

    protected abstract void setImpl(int position, Object element);

    public Object dequeue() {
        assert size > 0;

        Object result = elementImpl();
        dequeueImpl();
        size--;
        return result;
    }

    protected abstract void dequeueImpl();

    public Object remove() {
        assert size > 0;

        Object result = peek();
        removeImpl();
        size--;
        return result;
    }

    protected abstract void removeImpl();

    public Object element() {
        assert size > 0;

        return elementImpl();
    }

    protected abstract Object elementImpl();

    public Object peek() {
        assert size > 0;

        return peekImpl();
    }

    protected abstract Object peekImpl();

    public Object get(int index) {
        assert size > index && index >= 0;

        return getImpl(index);
    }

    protected abstract Object getImpl(int index);

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        clearImpl();
        size = 0;
    }

    protected abstract void clearImpl();

    public Queue getNth(int n) {
        AbstractQueue queue = makeCopy();
        for (int i = 0; i < size; i++) {
            if (i % n == n - 1) {
                queue.enqueue(queue.element());
            }
            queue.dequeue();
        }
        return queue;
    }

    protected abstract AbstractQueue makeCopy();

    public Queue removeNth(int n) {
        Queue queue = getNth(n);
        dropNth(n);
        return queue;
    }

    public void dropNth(int n) {
        int tmpSize = size;
        for (int i = 0; i < tmpSize; i++) {
            if (i % n != n - 1) {
                enqueue(element());
            }
            dequeue();
        }
    }
}
