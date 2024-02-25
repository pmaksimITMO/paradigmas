package expression.parser;

import expression.exceptions.*;
import expression.exceptions.BaseParser;
import expression.exceptions.CharSource;
import expression.exceptions.StringSource;
import expression.generic.GenericTripleExpression;
import expression.generic.mods.OperationMode;
import expression.generic.operations.*;

public class GenericExpressionParser<T> implements GenericParser<T> {
    private final OperationMode<T> operationMode;

    public GenericExpressionParser(final OperationMode<T> operationMode) {
        this.operationMode = operationMode;
    }

    @Override
    public GenericTripleExpression<T> parse(String expression) throws ParserException {
        return parse(new StringSource(expression));
    }

    public GenericTripleExpression<T> parse(CharSource source) throws ParserException {
        return new Parser<>(source, operationMode).parseExpression();
    }

    private static class Parser<T> extends BaseParser {
        OperationMode<T> operationMode;

        protected Parser(final CharSource source, final OperationMode<T> operationMode) {
            super(source);
            this.operationMode = operationMode;
        }

        protected GenericTripleExpression<T> parseExpression() throws ParserException {
            GenericTripleExpression<T> result = parseAddSub();
            expect(END);
            return result;
        }

        private GenericTripleExpression<T> parseAddSub() throws ParserException {
            GenericTripleExpression<T> result = parseDivideMultiplyMod();
            while (true) {
                if (take('+')) {
                    result = new Add<>(result, parseDivideMultiplyMod(), operationMode);
                } else if (take('-')) {
                    result = new Subtract<>(result, parseDivideMultiplyMod(), operationMode);
                } else {
                    return result;
                }
            }
        }

        private GenericTripleExpression<T> parseDivideMultiplyMod() throws ParserException {
            GenericTripleExpression<T> result = parseUnaryVariableConst();
            while (true) {
                if (take('/')) {
                    result = new Divide<>(result, parseUnaryVariableConst(), operationMode);
                } else if (take('*')) {
                    result = new Multiply<>(result, parseUnaryVariableConst(), operationMode);
                } else if (take("mod")) {
                    result = new Mod<>(result, parseUnaryVariableConst(), operationMode);
                } else {
                    return result;
                }
            }
        }

        private GenericTripleExpression<T> parseUnaryVariableConst() throws ParserException {
            GenericTripleExpression<T> result = null;
            skipWhitespaces();
            if (take('(')) {
                result = parseAddSub();
                expect(')');
            } else if (take('-')) {
                if (between('0', '9')) {
                    result = parseNumber("-");
                } else {
                    result = new Negate<>(parseUnaryVariableConst(), operationMode);
                }
            } else if (take("abs")) {
                result = new Abs<>(parseUnaryVariableConst(), operationMode);
            } else if (take("square")) {
                result = new Square<>(parseUnaryVariableConst(), operationMode);
            } else if (between('0', '9')) {
                result = parseNumber("");
            } else if (isVariable(getToken())) {
                result = new Variable<>(getToken());
                takeToken();
            }
            skipWhitespaces();
            return result;
        }

        private boolean isVariable(String token) {
            return token.equals("x") || token.equals("y") || token.equals("z");
        }

        private GenericTripleExpression<T> parseNumber(String prefixMinus) throws ParserException {
            StringBuilder result = new StringBuilder(prefixMinus);
            while (between('0', '9')) {
                result.append(take());
            }
            try {
                return new Const<>(operationMode.parse(result.toString()));
            } catch (NumberFormatException e) {
                throw new ConstOverflowException("Invalid input: overflow const in position " + getErrorPosition());
            }
        }

        private void skipWhitespaces() {
            while (isWhitespace()) {
                take();
            }
        }
    }
}
