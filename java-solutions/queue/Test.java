package queue;

public class Test {
    String operation;

    Object element; // not necessarily

    int index; // not necessarily

    public Test(String operation, Object element, int index) {
        this.operation = operation;
        this.element = element;
        this.index = index;
    }

    @Override
    public String toString() {
        return "(command)" + operation + " (element)" + element + " (index)" + index;
    }
}
