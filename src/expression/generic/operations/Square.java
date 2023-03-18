package expression.generic.operations;

import expression.generic.GenericTripleExpression;
import expression.generic.mods.OperationMode;

public class Square<T> extends UnaryGenericOperation<T> {
    public Square(GenericTripleExpression<T> value, OperationMode<T> operationMode) {
        super(value, operationMode);
    }

    @Override
    protected T calculate(T value) {
        return operationMode.square(value);
    }
}
