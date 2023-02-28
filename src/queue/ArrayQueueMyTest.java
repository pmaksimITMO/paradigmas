package queue;

import java.util.Objects;

public class ArrayQueueMyTest {
    public static void check(ArrayQueue queue, String operation) {
        try {
            switch (operation) {
                case "isEmpty" -> System.out.println(queue.isEmpty());
                case "size" -> System.out.println(queue.size());
                case "peek" -> System.out.println(queue.peek());
                case "remove" -> queue.remove();
                case "element" -> System.out.println(queue.element());
                case "dequeue" -> System.out.println(queue.dequeue());
            }
            System.out.println("Correct operation: " + operation);
        } catch (Throwable e) {
            System.out.println("Incorrect operation: " + operation);
        }
    }

    public static void checkGETSET(ArrayQueue queue, String operation, int index, Object value) {
        try {
            switch (operation) {
                case "get" -> System.out.println(queue.get(index));
                case "set" -> queue.set(index, value);
            }
            System.out.println("Correct operation: " + operation + " " + index + " " + value);
        } catch (Throwable e) {
            System.err.println("Incorrect operation: " + operation + " " + index
                    + (Objects.equals(operation, "get") ? "" : " " + value));
        }
    }

    public static void check(ArrayQueue queue, String operation, Object value) {
        try {
            switch (operation) {
                case "push" -> queue.push(value);
                case "enqueue" -> queue.enqueue(value);
            }
            System.out.println("Correct operation: " + operation + " " + value);
        } catch (Throwable e) {
            System.out.println("Incorrect operation: " + operation + " " + value);
        }
    }

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();
        check(queue, "push", null);
        check(queue, "size");
        check(queue, "isEmpty");
        check(queue, "enqueue", null);
        check(queue, "push", 1);
        checkGETSET(queue, "get", 1, 1);
        checkGETSET(queue, "set", 0, 2);
        checkGETSET(queue, "set", -2, 2);
    }
}
