package queue;

import java.util.*;

public class ArrayQueueModuleTest {

    public static final List<String> command = List.of(
            "isEmpty", "size", "peek", "remove", "element", "dequeue", "clear",
            "get", "set", "push", "enqueue"
    );

    public static final List<Object> element = List.of(
            1, Integer.MIN_VALUE, Integer.MAX_VALUE, "asd", new Object() , true
    );

    public static Deque<Object> testQueue = new ArrayDeque<>();

    public static Object check(Test test) {
        try {
            Object result = true;
            switch (test.operation) {
                case "isEmpty" -> result = testQueue.isEmpty();
                case "size" -> result = testQueue.size();
                case "peek" -> result = testQueue.isEmpty() ? "Error" : testQueue.getLast();
                case "remove" -> result = testQueue.isEmpty() ? "Error" : testQueue.removeLast();
                case "element" -> result = testQueue.isEmpty() ? "Error" : testQueue.element();
                case "dequeue" -> result = testQueue.isEmpty() ? "Error" : testQueue.removeFirst();
                case "clear" -> testQueue.clear();
                case "push" -> {
                    testQueue.push(test.element);
                    result = testQueue.element();
                }
                case "enqueue" -> {
                    testQueue.add(test.element);
                    result = testQueue.getLast();
                }
                case "get" -> {
                    if (test.index < 0 || test.index >= testQueue.size()) {
                        result = "Error";
                    } else {
                        Deque<Object> copy = new ArrayDeque<>(testQueue);
                        for (int i = 0; i < copy.size(); i++) {
                            if (i == test.index) result = testQueue.element();
                            testQueue.removeFirst();
                        }
                        testQueue = copy;
                    }
                }
                case "set" -> {
                    if (test.index < 0 || test.index >= testQueue.size()) {
                        result = "Error";
                    } else {
                        Deque<Object> copy = new ArrayDeque<>();
                        int length = testQueue.size();
                        for (int i = 0; i < length; i++) {
                            if (i == test.index) {
                                copy.add(test.element);
                                testQueue.removeFirst();
                            } else copy.add(testQueue.removeFirst());
                        }
                        testQueue = copy;
                    }
                }
            }
            return result;
        } catch (Throwable e) {
            return "Error";
        }
    }

    public static Object checkMySolve(Test test) {
        try {
            Object result = true;
            switch (test.operation) {
                case "isEmpty" -> result = ArrayQueueModule.isEmpty();
                case "size" -> result = ArrayQueueModule.size();
                case "peek" -> result = ArrayQueueModule.peek();
                case "remove" -> result = ArrayQueueModule.remove();
                case "element" -> result = ArrayQueueModule.element();
                case "dequeue" -> result = ArrayQueueModule.dequeue();
                case "clear" -> {
                    ArrayQueueModule.clear();
                    result = ArrayQueueModule.size() == 0;
                }
                case "push" -> {
                    ArrayQueueModule.push(test.element);
                    result = ArrayQueueModule.element();
                }
                case "enqueue" -> {
                    ArrayQueueModule.enqueue(test.element);
                    result = ArrayQueueModule.peek();
                }
                case "get" -> result = ArrayQueueModule.get(test.index);
                case "set" -> ArrayQueueModule.set(test.index, test.element);
            }
            return result;
        } catch (Throwable e) {
            return "Error";
        }
    }

    public static void test(Test test) {
        final Object res1 = check(test);
        final Object res2 = checkMySolve(test);
        if (!Objects.equals(res1, res2)) {
            throw new IllegalStateException(
                    "Undefined behaviour: " + test +
                            "\nexpected: " + res1 +
                            "\nfound: " + res2
            );
        }
    }

    public static void main(String[] args) {
        Random rnd = new Random(8045702385702345702L);
        int N = 100000;
        StringBuilder sb = new StringBuilder();
        sb.append("Testing:\n");
        sb.append("\tNull tests: ");
        for (int i = 0; i < N; i++) {
            Test newTest = new Test(
                    command.get(rnd.nextInt(command.size())),
                    null,
                    rnd.nextInt(testQueue.isEmpty() ? 1 : testQueue.size())
            );
            test(newTest);
        }
        sb.append("OK\n").append("\tRandom tests:");
        for (int i = 0; i < N; i++) {
            Test newTest = new Test(
                    command.get(rnd.nextInt(command.size())),
                    element.get(rnd.nextInt(element.size())),
                    rnd.nextInt(testQueue.isEmpty() ? 1 : testQueue.size())
            );
            test(newTest);
        }
        sb.append("OK\n").append("All tests passed");
        System.out.println(sb);
    }
}
