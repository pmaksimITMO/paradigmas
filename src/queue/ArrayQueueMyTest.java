package queue;

public class ArrayQueueMyTest {
    public static void fill(ArrayQueue queue, String word) {
        for (int i = 0; i < 5; i++) {
            queue.enqueue(word + i);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(
                    queue.size() + " " +
                    queue.element() + " " +
                    queue.dequeue()
            );
        }
    }

    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();
        fill(queue1, "Hello");
        fill(queue2, "world");
        dump(queue1);
        dump(queue2);
    }
}
