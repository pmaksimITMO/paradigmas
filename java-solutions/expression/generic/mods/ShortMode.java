package expression.generic.mods;

public class ShortMode implements OperationMode<Short> {
    @Override
    public Short add(Short a, Short b) {
        return (short) (a + b);
    }

    @Override
    public Short subtract(Short a, Short b) {
        return (short) (a - b);
    }

    @Override
    public Short multiply(Short a, Short b) {
        return (short) (a * b);
    }

    @Override
    public Short divide(Short a, Short b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return (short) (a / b);
    }

    @Override
    public Short negate(Short a) {
        return (short) -a;
    }

    @Override
    public Short parse(String expression) {
        return (short) Integer.parseInt(expression);
    }

    @Override
    public Short abs(Short a) {
        return (short) (a >= 0 ? a : -a);
    }

    @Override
    public Short square(Short a) {
        return (short) (a * a);
    }

    @Override
    public Short mod(Short a, Short b) {
        return (short) (a % b);
    }
}
