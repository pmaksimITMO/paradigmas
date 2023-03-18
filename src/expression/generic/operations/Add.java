package expression.generic.operations;

import expression.generic.GenericTripleExpression;
import expression.generic.mods.OperationMode;

public class Add<T> extends BinaryGenericOperation<T> {
    public Add(
            GenericTripleExpression<T> left,
            GenericTripleExpression<T> right,
            OperationMode<T> operationMode
    ) {
        super(left, right, operationMode);
    }

    @Override
    protected T calculate(T l, T r) {
        return operationMode.add(l, r);
    }
}
