package queue;

public class ArrayQueueADTTest {
    public static void fill(ArrayQueueADT queue, String prefix) {
        for (int i = 0; i < prefix.length(); i++) {
            ArrayQueueADT.enqueue(queue, prefix + i);
        }
    }

    public static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                    ArrayQueueADT.size(queue) + " " +
                    ArrayQueueADT.element(queue) + " " +
                    ArrayQueueADT.dequeue(queue)
            );
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT queue1 = ArrayQueueADT.create();
        ArrayQueueADT queue2 = ArrayQueueADT.create();
        fill(queue1, "Hello");
        fill(queue2, "world");
        dump(queue1);
        dump(queue2);
    }
}
