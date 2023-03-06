package queue;

import java.util.*;

public class ArrayQueueMyTest {
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

    public static Object check(ArrayQueue queue, Test test) {
        try {
            Object result = true;
            switch (test.operation) {
                case "isEmpty" -> result = queue.isEmpty();
                case "size" -> result = queue.size();
                case "peek" -> result = queue.peek();
                case "remove" -> result = queue.remove();
                case "element" -> result = queue.element();
                case "dequeue" -> result = queue.dequeue();
                case "clear" -> {
                    queue.clear();
                    result = queue.size() == 0;
                }
                case "push" -> {
                    queue.push(test.element);
                    result = queue.element();
                }
                case "enqueue" -> {
                    queue.enqueue(test.element);
                    result = queue.peek();
                }
                case "get" -> result = queue.get(test.index);
                case "set" -> queue.set(test.index, test.element);
            }
            return result;
        } catch (Throwable e) {
            return "Error";
        }
    }

    public static void test(ArrayQueue queue, Test test) {
        final Object res1 = check(test);
        final Object res2 = check(queue, test);
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
        ArrayQueue queue = new ArrayQueue();
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
            test(queue, newTest);
        }
        sb.append("OK\n").append("\tRandom tests:");
        for (int i = 0; i < N; i++) {
            Test newTest = new Test(
                    command.get(rnd.nextInt(command.size())),
                    element.get(rnd.nextInt(element.size())),
                    rnd.nextInt(testQueue.isEmpty() ? 1 : testQueue.size())
            );
            test(queue, newTest);
        }
        sb.append("OK\n").append("All tests passed");
        System.out.println(sb);
    }
}
