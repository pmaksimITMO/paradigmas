package expression.generic.operations;

import expression.generic.GenericTripleExpression;

public class Variable<T> implements GenericTripleExpression<T> {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        T result = null;
        switch (name) {
            case "x" -> result = x;
            case "y" -> result = y;
            case "z" -> result = z;
            default -> throw new IllegalArgumentException("Invalid variable name");
        }
        return result;
    }
}
