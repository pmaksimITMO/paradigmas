package queue;

public class ArrayQueueModuleTest {
    public static void fillFront() {
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.enqueue(i);
        }
    }

    public static void fillBack() {
        for (int i = 0; i < 5; i++) {
            ArrayQueueModule.push(i);
        }
    }

    public static void dumpBack() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    ArrayQueueModule.size() + " " +
                    ArrayQueueModule.peek() + " " +
                    ArrayQueueModule.remove()
            );
        }
    }

    public static void dumpFront() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println(
                    ArrayQueueModule.size() + " " +
                    ArrayQueueModule.element() + " " +
                    ArrayQueueModule.dequeue()
            );
        }
    }

    public static void update(String value, int index) {
        ArrayQueueModule.set(index, value);
        System.out.println(ArrayQueueModule.get(index));
    }

    public static void main(String[] args) {
        fillFront();
        dumpBack();
        fillBack();
        update("OMG", 1);
        dumpFront();
    }
}
