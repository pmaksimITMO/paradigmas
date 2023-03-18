package expression.generic.mods;

public class IntegerMode implements OperationMode<Integer> {
    private final boolean needCheckOverflow;

    public IntegerMode(boolean needCheckOverflow) {
        this.needCheckOverflow = needCheckOverflow;
    }

    @Override
    public Integer add(Integer a, Integer b) {
        if (needCheckOverflow && hasAddError(a, b)) {
            throw new ArithmeticException("Overflow in add: a = " + a + ", b = " + b);
        }
        return a + b;
    }

    private boolean hasAddError(Integer a, Integer b) {
        return (a > 0 && Integer.MAX_VALUE - a < b || a < 0 && Integer.MIN_VALUE - a > b);
    }


    @Override
    public Integer subtract(Integer a, Integer b) {
        if (needCheckOverflow && hasSubtractError(a, b)) {
            throw new ArithmeticException("Overflow in subtract: a = " + a + ", b = " + b);
        }
        return a - b;
    }

    private boolean hasSubtractError(Integer a, Integer b) {
        return (b > 0 && Integer.MIN_VALUE + b > a || b < 0 && Integer.MAX_VALUE + b < a);
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        if (needCheckOverflow && hasMultiplyError(a, b)) {
            throw new ArithmeticException("Overflow in multiply: a = " + a + ", b = " + b);
        }
        return a * b;
    }

    private boolean hasMultiplyError(Integer a, Integer b) {
        return (a < 0 && (b < 0 && Integer.MAX_VALUE / b > a || b > 0 && Integer.MIN_VALUE / b > a))
                || (a > 0 && (b < 0 && Integer.MIN_VALUE / a > b || b > 0 && Integer.MAX_VALUE / a < b));
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (needCheckOverflow) {
            checkDivideError(a, b);
        }
        return a / b;
    }

    private void checkDivideError(Integer a, Integer b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (b == -1 && a == Integer.MIN_VALUE) {
            throw new ArithmeticException("Overflow in divide: a = " + a + ", b = " + b);
        }
    }

    @Override
    public Integer negate(Integer a) {
        if (needCheckOverflow && a == Integer.MIN_VALUE) {
            throw new ArithmeticException("Overflow in negate: a = " + a);
        }
        return -a;
    }

    @Override
    public Integer parse(String expression) {
        return Integer.parseInt(expression);
    }

    @Override
    public Integer abs(Integer a) {
        if (needCheckOverflow && a == Integer.MIN_VALUE) {
            throw new ArithmeticException("Overflow in abs: a = " + a);
        }
        return a >= 0 ? a : -a;
    }

    @Override
    public Integer square(Integer a) {
        if (needCheckOverflow && hasSquareError(a)) {
            throw new ArithmeticException("Overflow in square: a = " + a);
        }
        return a * a;
    }

    private boolean hasSquareError(Integer a) {
        return a > Math.sqrt(Integer.MAX_VALUE) || a < -Math.sqrt(Integer.MAX_VALUE);
    }

    @Override
    public Integer mod(Integer a, Integer b) {
        if (needCheckOverflow && b == 0) {
            throw new ArithmeticException("Overflow in mod: a = " + a + ", b = " + b);
        }
        return a % b;
    }
}
