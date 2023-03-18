package expression.generic.operations;

import expression.generic.GenericTripleExpression;
import expression.generic.mods.OperationMode;

public abstract class UnaryGenericOperation<T> implements GenericTripleExpression<T> {
    private final GenericTripleExpression<T> value;

    protected final OperationMode<T> operationMode;

    public UnaryGenericOperation(GenericTripleExpression<T> value, OperationMode<T> operationMode) {
        this.value = value;
        this.operationMode = operationMode;
    }

    protected abstract T calculate(T value);

    @Override
    public T evaluate(T x, T y, T z) {
        return calculate(value.evaluate(x, y, z));
    }
}
