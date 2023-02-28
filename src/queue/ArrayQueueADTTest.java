package queue;

import java.util.Objects;

public class ArrayQueueADTTest {
    public static void check(ArrayQueueADT queue, String operation) {
        try {
            switch (operation) {
                case "isEmpty" -> System.out.println(ArrayQueueADT.isEmpty(queue));
                case "size" -> System.out.println(ArrayQueueADT.size(queue));
                case "peek" -> System.out.println(ArrayQueueADT.peek(queue));
                case "remove" -> ArrayQueueADT.remove(queue);
                case "element" -> System.out.println(ArrayQueueADT.element(queue));
                case "dequeue" -> System.out.println(ArrayQueueADT.dequeue(queue));
            }
            System.out.println("Correct operation: " + operation);
        } catch (Throwable e) {
            System.out.println("Incorrect operation: " + operation);
        }
    }

    public static void checkGETSET(ArrayQueueADT queue, String operation, int index, Object value) {
        try {
            switch (operation) {
                case "get" -> System.out.println(ArrayQueueADT.get(queue, index));
                case "set" -> ArrayQueueADT.set(queue, index, value);
            }
            System.out.println("Correct operation: " + operation + " " + index + " " + value);
        } catch (Throwable e) {
            System.err.println("Incorrect operation: " + operation + " " + index
                    + (Objects.equals(operation, "get") ? "" : " " + value));
        }
    }

    public static void check(ArrayQueueADT queue, String operation, Object value) {
        try {
            switch (operation) {
                case "push" -> ArrayQueueADT.push(queue, value);
                case "enqueue" -> ArrayQueueADT.enqueue(queue, value);
            }
            System.out.println("Correct operation: " + operation + " " + value);
        } catch (Throwable e) {
            System.out.println("Incorrect operation: " + operation + " " + value);
        }
    }


    public static void main(String[] args) {
        ArrayQueueADT queue = ArrayQueueADT.create();
        check(queue, "push", null);
        check(queue, "size");
        check(queue, "isEmpty");
        check(queue, "enqueue", null);
        check(queue, "push", 1);
        checkGETSET(queue, "get", 1, 1);
        checkGETSET(queue, "set", 0, 2);
        checkGETSET(queue, "set", -1, 2);
    }
}
