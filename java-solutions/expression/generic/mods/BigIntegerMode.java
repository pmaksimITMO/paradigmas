package expression.generic.mods;

import java.math.BigInteger;

public class BigIntegerMode implements OperationMode<BigInteger> {
    @Override
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger divide(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("Division by zero");
        }
        return a.divide(b);
    }

    @Override
    public BigInteger negate(BigInteger a) {
        return a.negate();
    }

    @Override
    public BigInteger parse(String expression) {
        return new BigInteger(expression);
    }

    @Override
    public BigInteger abs(BigInteger a) {
        return a.abs();
    }

    @Override
    public BigInteger square(BigInteger a) {
        return a.multiply(a);
    }

    @Override
    public BigInteger mod(BigInteger a, BigInteger b) {
        return a.mod(b);
    }
}
