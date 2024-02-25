package queue;

import java.util.*;

public class ArrayQueueADTTest {

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

    public static List<Object> check(ArrayQueueADT queue, Test test) {
        try {
            List<Object> result = new ArrayList<>();
            switch (test.operation) {
                case "isEmpty" -> result.add(ArrayQueueADT.isEmpty(queue));
                case "size" -> result.add(ArrayQueueADT.size(queue));
                case "peek" -> result.add(ArrayQueueADT.peek(queue));
                case "remove" -> {
                    ArrayQueueADT.remove(queue);
                    result = myQueueToList(queue);
                }
                case "element" -> result.add(ArrayQueueADT.element(queue));
                case "dequeue" -> {
                    ArrayQueueADT.dequeue(queue);
                    result = myQueueToList(queue);
                }
                case "clear" -> {
                    ArrayQueueADT.clear(queue);
                    result.add(ArrayQueueADT.size(queue) == 0);
                }
                case "push" -> {
                    ArrayQueueADT.push(queue, test.element);
                    result = myQueueToList(queue);
                }
                case "enqueue" -> {
                    ArrayQueueADT.enqueue(queue, test.element);
                    result = myQueueToList(queue);
                }
                case "get" -> result.add(ArrayQueueADT.get(queue, test.index));
                case "set" -> {
                    ArrayQueueADT.set(queue, test.index, test.element);
                    result = myQueueToList(queue);
                }
            }
            return result;
        } catch (Throwable e) {
            return List.of("Error");
        }
    }

    public static List<Object> myQueueToList(ArrayQueueADT queue) {
        int size = ArrayQueueADT.size(queue);
        List<Object> list = new ArrayList<>();
        while (size > 0) {
            Object element = ArrayQueueADT.element(queue);
            list.add(element);
            ArrayQueueADT.enqueue(queue, element);
            ArrayQueueADT.dequeue(queue);
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

    public static void test(ArrayQueueADT queue, Test test) {
        final List<Object> res1 = check(test);
        final List<Object> res2 = check(queue, test);
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
        ArrayQueueADT queue = new ArrayQueueADT();
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
