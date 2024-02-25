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

    public static List<Object> check(Test test) {
        try {
            List<Object> result = new ArrayList<>();
            switch (test.operation) {
                case "isEmpty" -> result.add(testQueue.isEmpty());
                case "size" -> result.add(testQueue.size());
                case "peek" -> result.add(testQueue.isEmpty() ? "Error" : testQueue.getLast());
                case "remove" -> {
                    if (testQueue.isEmpty()) {
                        result.add("Error");
                    } else {
                        testQueue.removeLast();
                        result = queueToList(testQueue);
                    }
                }
                case "element" -> result.add(testQueue.isEmpty() ? "Error" : testQueue.element());
                case "dequeue" -> {
                    if (testQueue.isEmpty()) {
                        result.add("Error");
                    } else {
                        testQueue.removeFirst();
                        result = queueToList(testQueue);
                    }
                }
                case "clear" -> {
                    testQueue.clear();
                    result.add(true);
                }
                case "push" -> {
                    testQueue.push(test.element);
                    result = queueToList(testQueue);
                }
                case "enqueue" -> {
                    testQueue.add(test.element);
                    result = queueToList(testQueue);
                }
                case "get" -> {
                    if (test.index < 0 || test.index >= testQueue.size()) {
                        result.add("Error");
                    } else {
                        Deque<Object> copy = new ArrayDeque<>(testQueue);
                        for (int i = 0; i < copy.size(); i++) {
                            if (i == test.index) result.add(testQueue.element());
                            testQueue.removeFirst();
                        }
                        testQueue = copy;
                    }
                }
                case "set" -> {
                    if (test.index < 0 || test.index >= testQueue.size()) {
                        result.add("Error");
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
                        result = queueToList(testQueue);
                    }
                }
            }
            return result;
        } catch (Throwable e) {
            return List.of("Error");
        }
    }

    public static List<Object> checkMySolve(Test test) {
        try {
            List<Object> result = new ArrayList<>();
            switch (test.operation) {
                case "isEmpty" -> result.add(ArrayQueueModule.isEmpty());
                case "size" -> result.add(ArrayQueueModule.size());
                case "peek" -> result.add(ArrayQueueModule.peek());
                case "remove" -> {
                    ArrayQueueModule.remove();
                    result = myQueueToList();
                }
                case "element" -> result.add(ArrayQueueModule.element());
                case "dequeue" -> {
                    ArrayQueueModule.dequeue();
                    result = myQueueToList();
                }
                case "clear" -> {
                    ArrayQueueModule.clear();
                    result.add(ArrayQueueModule.size() == 0);
                }
                case "push" -> {
                    ArrayQueueModule.push(test.element);
                    result = myQueueToList();
                }
                case "enqueue" -> {
                    ArrayQueueModule.enqueue(test.element);
                    result = myQueueToList();
                }
                case "get" -> result.add(ArrayQueueModule.get(test.index));
                case "set" -> {
                    ArrayQueueModule.set(test.index, test.element);
                    result = myQueueToList();
                }
            }
            return result;
        } catch (Throwable e) {
            return List.of("Error");
        }
    }

    public static List<Object> myQueueToList() {
        int size = ArrayQueueModule.size();
        List<Object> list = new ArrayList<>();
        while (size > 0) {
            Object element = ArrayQueueModule.element();
            list.add(element);
            ArrayQueueModule.enqueue(element);
            ArrayQueueModule.dequeue();
            size--;
        }
        return list;
    }

    public static List<Object> queueToList(Deque<Object> queue) {
        int size = queue.size();
        List<Object> list = new ArrayList<>();
        while (size > 0) {
            list.add(queue.element());
            queue.add(queue.element());
            queue.removeFirst();
            size--;
        }
        return list;
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
