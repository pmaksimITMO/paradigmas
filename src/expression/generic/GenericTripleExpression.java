package expression.generic;

public interface GenericTripleExpression<T> {
    T evaluate(T x, T y, T z);
}
