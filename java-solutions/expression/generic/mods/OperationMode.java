package expression.generic.mods;

public interface OperationMode<T> {
    T add(T a, T b);
    T subtract(T a, T b);
    T multiply(T a, T b);
    T divide(T a, T b);
    T negate(T a);
    T parse(String expression);
    T abs(T a);
    T square(T a);
    T mod(T a, T b);
}
