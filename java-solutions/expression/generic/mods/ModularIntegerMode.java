package expression.generic.mods;

public class ModularIntegerMode implements OperationMode<Integer> {
    private final int MODULE;

    public ModularIntegerMode(int MODULE) {
        this.MODULE = MODULE;
    }

    @Override
    public Integer add(Integer a, Integer b) {
        return (a + b) % MODULE;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return (a - b + MODULE) % MODULE;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return (a * b) % MODULE;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return (a * modularMultiplicativeInverse(b, MODULE - 2, MODULE)) % MODULE;
    }

    private static int  modularMultiplicativeInverse(int a, int n, int mod) {
        int res = 1;
        while (n > 0) {
            if ((n & 1) == 1) {
                res = (res * a) % mod;
                n--;
            } else {
                a = (a * a) % mod;
                n /= 2;
            }
        }
        return res;
    }

    @Override
    public Integer negate(Integer a) {
        return (-a + MODULE) % MODULE;
    }

    @Override
    public Integer parse(String expression) {
        return (Integer.parseInt(expression) % MODULE + MODULE) % MODULE;
    }

    @Override
    public Integer abs(Integer a) {
        return (a >= 0 ? a : -a) % MODULE;
    }

    @Override
    public Integer square(Integer a) {
        return (a * a) % MODULE;
    }

    @Override
    public Integer mod(Integer a, Integer b) {
        return (a % b) % MODULE;
    }
}
