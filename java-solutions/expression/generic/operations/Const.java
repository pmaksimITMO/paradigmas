package expression.generic.operations;

import expression.generic.GenericTripleExpression;

public class Const<T> implements GenericTripleExpression<T> {
    private final T value;

    public Const(T value) {
        this.value = value;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return value;
    }
}
