package queue;

import java.util.*;

public class QueueMyTest {
    public static final List<String> command = List.of(
            "isEmpty", "size", "peek", "remove", "element", "dequeue", "clear",
            "get", "set", "push", "enqueue", "getNth", "removeNth", "dropNth"
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
                case "remove" -> result.add(testQueue.isEmpty() ? "Error" : testQueue.removeLast());
                case "element" -> result.add(testQueue.isEmpty() ? "Error" : testQueue.element());
                case "dequeue" -> result.add(testQueue.isEmpty() ? "Error" : testQueue.removeFirst());
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
                        result = List.of("Error");
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
                        result = List.of("Error");
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
                case "getNth" -> {
                    int length = testQueue.size();
                    Deque<Object> newDeque = new ArrayDeque<>();
                    for (int i = 0; i < length; i++) {
                        if (i % test.index == test.index - 1) {
                            newDeque.add(testQueue.element());
                        }
                        testQueue.add(testQueue.element());
                        testQueue.removeFirst();
                    }
                    result = queueToList(newDeque);
                }
                case "dropNth" -> {
                    int length = testQueue.size();
                    for (int i = 0; i < length; i++) {
                        if (i % test.index != test.index - 1) {
                            testQueue.add(testQueue.element());
                        }
                        testQueue.removeFirst();
                    }
                    result = queueToList(testQueue);
                }
                case "removeNth" -> {
                    result = check(new Test("getNth", test.element, test.index));
                    check(new Test("dropNth", test.element, test.index));
                }
            }
            return result;
        } catch (Throwable e) {
            return List.of("Error");
        }
    }

    public static List<Object> check(Queue queue, Test test) {
        try {
            List<Object> result = new ArrayList<>();
            switch (test.operation) {
                case "isEmpty" -> result.add(queue.isEmpty());
                case "size" -> result.add(queue.size());
                case "peek" -> result.add(queue.peek());
                case "remove" -> result.add(queue.remove());
                case "element" -> result.add(queue.element());
                case "dequeue" -> result.add(queue.dequeue());
                case "clear" -> {
                    queue.clear();
                    result.add(queue.size() == 0);
                }
                case "push" -> {
                    queue.push(test.element);
                    result = myQueueToList(queue);
                }
                case "enqueue" -> {
                    queue.enqueue(test.element);
                    result = myQueueToList(queue);
                }
                case "get" -> result.add(queue.get(test.index));
                case "set" -> {
                    queue.set(test.index, test.element);
                    result = myQueueToList(queue);
                }
                case "getNth" -> {
                    Queue newQueue = queue.getNth(test.index);
                    return myQueueToList(newQueue);
                }
                case "dropNth" -> {
                    queue.dropNth(test.index);
                    result = myQueueToList(queue);
                }
                case "removeNth" -> {
                    Queue newQueue = queue.getNth(test.index);
                    queue.dropNth(test.index);
                    return myQueueToList(newQueue);
                }
            }
            return result;
        } catch (Throwable e) {
            return List.of("Error");
        }
    }

    public static List<Object> myQueueToList(Queue queue) {
        int size = queue.size();
        List<Object> list = new ArrayList<>();
        while (size > 0) {
            list.add(queue.element());
            queue.enqueue(queue.element());
            queue.dequeue();
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

    public static void test(ArrayQueue arrayQueue, LinkedQueue linkedQueue, Test test) {
        final List<Object> res1 = check(test);
        final List<Object> res2 = check(arrayQueue, test);
        final List<Object> res3 = check(linkedQueue, test);
        if (!Objects.equals(res1, res2) || !Objects.equals(res1, res3) || !Objects.equals(res2, res3)) {
            throw new IllegalStateException(
                    "Undefined behaviour: " + test +
                            "\nexpected: " + res1 +
                            "\nfound ArrayQueue: " + res2 +
                            "\nfound LinkedQueue: " + res3
            );
        }
    }

    public static void main(String[] args) {
        Random rnd = new Random(8045702385702345702L);
        ArrayQueue arrayQueue = new ArrayQueue();
        LinkedQueue linkedQueue = new LinkedQueue();
        int N = 100000;
        StringBuilder sb = new StringBuilder();
        sb.append("Testing:\n");
        sb.append("\tNull tests: ");
        for (int i = 0; i < N; i++) {
            Test newTest = new Test(
                    command.get(rnd.nextInt(command.size())),
                    null,
                    1 + rnd.nextInt(testQueue.isEmpty() ? 1 : testQueue.size())
            );
            test(arrayQueue, linkedQueue, newTest);
        }
        sb.append("OK\n").append("\tRandom tests:");
        for (int i = 0; i < N; i++) {
            Test newTest = new Test(
                    command.get(rnd.nextInt(command.size())),
                    element.get(rnd.nextInt(element.size())),
                    1 + rnd.nextInt(testQueue.isEmpty() ? 1 : testQueue.size())
            );
            test(arrayQueue, linkedQueue, newTest);
        }
        sb.append("OK\n").append("All tests passed");
        System.out.println(sb);
    }
}
