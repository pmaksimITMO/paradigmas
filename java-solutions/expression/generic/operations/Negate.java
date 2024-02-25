package expression.generic.operations;

import expression.generic.GenericTripleExpression;
import expression.generic.mods.OperationMode;

public class Negate<T> extends UnaryGenericOperation<T> {
    public Negate(GenericTripleExpression<T> value, OperationMode<T> operationMode) {
        super(value, operationMode);
    }

    @Override
    protected T calculate(T value) {
        return operationMode.negate(value);
    }
}
