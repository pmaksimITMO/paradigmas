package expression.generic.operations;

import expression.generic.GenericTripleExpression;
import expression.generic.mods.OperationMode;

public class Subtract<T> extends BinaryGenericOperation<T> {
    public Subtract(
            GenericTripleExpression<T> left,
            GenericTripleExpression<T> right,
            OperationMode<T> operationMode
    ) {
        super(left, right, operationMode);
    }

    @Override
    protected T calculate(T l, T r) {
        return operationMode.subtract(l, r);
    }
}
