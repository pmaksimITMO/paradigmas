package queue;

import java.util.Objects;

public class ArrayQueueModuleTest {

    public static void check(String operation) {
        try {
            switch (operation) {
                case "isEmpty" -> System.out.println(ArrayQueueModule.isEmpty());
                case "size" -> System.out.println(ArrayQueueModule.size());
                case "peek" -> System.out.println(ArrayQueueModule.peek());
                case "remove" -> ArrayQueueModule.remove();
                case "element" -> System.out.println(ArrayQueueModule.element());
                case "dequeue" -> System.out.println(ArrayQueueModule.dequeue());
            }
            System.out.println("Correct operation: " + operation);
        } catch (Throwable e) {
            System.out.println("Incorrect operation: " + operation);
        }
    }

    public static void checkGETSET(String operation, int index, Object value) {
        try {
            switch (operation) {
                case "get" -> System.out.println(ArrayQueueModule.get(index));
                case "set" -> ArrayQueueModule.set(index, value);
            }
            System.out.println("Correct operation: " + operation + " " + index + " " + value);
        } catch (Throwable e) {
            System.err.println("Incorrect operation: " + operation + " " + index
                    + (Objects.equals(operation, "get") ? "" : " " + value));
        }
    }

    public static void check(String operation, Object value) {
        try {
            switch (operation) {
                case "push" -> ArrayQueueModule.push(value);
                case "enqueue" -> ArrayQueueModule.enqueue(value);
            }
            System.out.println("Correct operation: " + operation + " " + value);
        } catch (Throwable e) {
            System.out.println("Incorrect operation: " + operation + " " + value);
        }
    }

    public static void main(String[] args) {
        check("push", null);
        check("size");
        check("isEmpty");
        check("enqueue", null);
        check("push", 1);
        checkGETSET("get", 1, 0);
        checkGETSET("set", 0, 2);
        checkGETSET("set", -1, 2);
    }
}
