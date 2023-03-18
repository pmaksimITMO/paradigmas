package expression.generic.operations;

import expression.generic.GenericTripleExpression;
import expression.generic.mods.OperationMode;

public abstract class BinaryGenericOperation<T> implements GenericTripleExpression<T> {
    private final GenericTripleExpression<T> left, right;

    protected final OperationMode<T> operationMode;

    public BinaryGenericOperation(GenericTripleExpression<T> left, GenericTripleExpression<T> right, OperationMode<T> operationMode) {
        this.left = left;
        this.right = right;
        this.operationMode = operationMode;
    }

    protected abstract T calculate(T l, T r);

    @Override
    public T evaluate(T x, T y, T z) {
        return calculate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }
}
