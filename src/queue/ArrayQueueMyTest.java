package queue;

public class ArrayQueueMyTest {
    public static void fillBack(ArrayQueue queue, String word) {
        for (int i = 0; i < 5; i++) {
            queue.enqueue(word + i);
        }
    }

    public static void fillFront(ArrayQueue queue, String word) {
        for (int i = 0; i < 5; i++) {
            queue.push(word + i);
        }
    }

    public static void dumpFront(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(
                    queue.size() + " " +
                    queue.element() + " " +
                    queue.dequeue()
            );
        }
    }

    public static void dumpBack(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(
                    queue.size() + " " +
                    queue.peek() + " " +
                    queue.remove()
            );
        }
    }

    public static void update(ArrayQueue queue, String value, int index) {
        queue.set(index, value);
        System.out.println(queue.get(index));
    }

    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        fillBack(queue1, "Hello");
        update(queue1, "OMG", 1);
        fillFront(queue2, "world");
        update(queue2, "OMG", 1);
        dumpFront(queue1);
        dumpBack(queue2);
    }
}
