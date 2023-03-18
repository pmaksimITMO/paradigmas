package expression.generic.operations;

import expression.generic.GenericTripleExpression;
import expression.generic.mods.OperationMode;

public class Divide<T> extends BinaryGenericOperation<T> {
    public Divide(
            GenericTripleExpression<T> left,
            GenericTripleExpression<T> right,
            OperationMode<T> operationMode
    ) {
        super(left, right, operationMode);
    }

    @Override
    protected T calculate(T l, T r) {
        return operationMode.divide(l, r);
    }
}
