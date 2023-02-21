package queue;

public class ArrayQueueADTTest {
    public static void fillBack(ArrayQueueADT queue, String prefix) {
        for (int i = 0; i < prefix.length(); i++) {
            ArrayQueueADT.enqueue(queue, prefix + i);
        }
    }

    public static void fillFront(ArrayQueueADT queue, String prefix) {
        for (int i = 0; i < prefix.length(); i++) {
            ArrayQueueADT.push(queue, prefix + i);
        }
    }

    public static void dumpBack(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                    ArrayQueueADT.size(queue) + " " +
                    ArrayQueueADT.peek(queue) + " " +
                    ArrayQueueADT.remove(queue)
            );
        }
    }

    public static void dumpFront(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(
                    ArrayQueueADT.size(queue) + " " +
                    ArrayQueueADT.element(queue) + " " +
                    ArrayQueueADT.dequeue(queue)
            );
        }
    }

    public static void update(ArrayQueueADT queue, String value, int index) {
        ArrayQueueADT.set(queue, index, value);
        System.out.println(ArrayQueueADT.get(queue, index));
    }

    public static void main(String[] args) {
        ArrayQueueADT queue1 = ArrayQueueADT.create();
        ArrayQueueADT queue2 = ArrayQueueADT.create();
        fillBack(queue1, "Hello");
        update(queue1, "OMG", 1);
        fillFront(queue2, "world");
        update(queue2, "OMG", 1);
        dumpFront(queue1);
        dumpBack(queue2);
    }
}
