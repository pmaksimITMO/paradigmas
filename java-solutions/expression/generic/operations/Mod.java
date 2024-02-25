package expression.generic.operations;

import expression.generic.GenericTripleExpression;
import expression.generic.mods.OperationMode;

public class Mod<T> extends BinaryGenericOperation<T> {
    public Mod(
            GenericTripleExpression<T> left,
            GenericTripleExpression<T> right,
            OperationMode<T> operationMode
    ) {
        super(left, right, operationMode);
    }

    @Override
    protected T calculate(T l, T r) {
        return operationMode.mod(l, r);
    }
}
