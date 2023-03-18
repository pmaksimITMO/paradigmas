package expression.generic.operations;

import expression.generic.GenericTripleExpression;
import expression.generic.mods.OperationMode;

public class Abs<T> extends UnaryGenericOperation<T> {
    public Abs(GenericTripleExpression<T> value, OperationMode<T> operationMode) {
        super(value, operationMode);
    }

    @Override
    protected T calculate(T value) {
        return operationMode.abs(value);
    }
}
