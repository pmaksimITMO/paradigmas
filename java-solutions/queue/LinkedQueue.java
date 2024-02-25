package queue;

import java.util.Objects;

public class LinkedQueue extends AbstractQueue {
    private Node head, tail;

    public LinkedQueue makeCopy() {
        LinkedQueue queue = new LinkedQueue();
        Node tmp = head;
        while (tmp != null) {
            queue.enqueue(tmp.value);
            tmp = tmp.next;
        }
        return queue;
    }

    protected void enqueueImpl(Object element) {
        Node newNode = new Node(element, null);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    protected void pushImpl(Object element) {
        Node newNode = new Node(element, head);
        if (size == 0) {
            tail = head = newNode;
        } else {
            head = newNode;
        }
    }

    protected void setImpl(int position, Object element) {
        Node current = head;
        for (int i = 0; i < position; i++) {
            current = current.next;
        }
        current.value = element;
    }

    protected void dequeueImpl() {
        if (size == 1) {
            head = tail = null;
        } else {
            head = head.next;
        }
    }

    protected void removeImpl() {
        if (size == 1) {
            head = tail = null;
        } else {
            Node tmp = head;
            while (tmp.next != tail) {
                tmp = tmp.next;
            }
            tail = tmp;
            tail.next = null;
        }
    }

    protected Object elementImpl() {
        return head.value;
    }

    protected Object peekImpl() {
        return tail.value;
    }

    protected Object getImpl(int index) {
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    protected void clearImpl() {
        tail = null;
        head = null;
    }

    private static class Node {
        private Object value;
        private Node next;

        public Node(Object value, Node next) {
            Objects.requireNonNull(value);

            this.value = value;
            this.next = next;
        }
    }
}
