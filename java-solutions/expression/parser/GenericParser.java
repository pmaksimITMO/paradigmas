package expression.parser;

import expression.exceptions.ParserException;
import expression.generic.GenericTripleExpression;

public interface GenericParser<T> {
    GenericTripleExpression<T> parse(String expression) throws ParserException;
}
