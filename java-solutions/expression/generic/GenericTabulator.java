package expression.generic;

import expression.exceptions.UnknownModeException;
import expression.generic.mods.*;
import expression.parser.GenericExpressionParser;

import java.util.Arrays;
import java.util.Map;

public class GenericTabulator implements Tabulator{
    Map<String, OperationMode<?>> MODS = Map.of(
            "i", new IntegerMode(true),
            "d", new DoubleMode(),
            "bi", new BigIntegerMode(),
            "u", new IntegerMode(false),
            "p", new ModularIntegerMode(10079),
            "s", new ShortMode()
    );
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        if (!MODS.containsKey(mode)) {
            throw new UnknownModeException("Unknown mode: " + mode);
        }
        return calculate(MODS.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] calculate(
            OperationMode<T> operationMode,
            String expressionString,
            int x1, int x2, int y1, int y2, int z1, int z2
    ) throws Exception {
        GenericExpressionParser<T> genericParser = new GenericExpressionParser<>(operationMode);
        GenericTripleExpression<T> expression = genericParser.parse(expressionString);
        Object[][][] result = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = 0; i < x2 - x1 + 1; i++) {
            for (int j = 0; j < y2 - y1 + 1; j++) {
                for (int k = 0; k < z2 - z1 + 1; k++) {
                    try {
                        result[i][j][k] = expression.evaluate(
                                operationMode.parse(Integer.toString( x1 + i)),
                                operationMode.parse(Integer.toString(y1 + j)),
                                operationMode.parse(Integer.toString(z1 + k))
                        );
                    } catch (ArithmeticException e) {
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalStateException("GenericTabulator need 2 arguments, found:" + Arrays.toString(args));
        }
        try {
            GenericTabulator tabulator = new GenericTabulator();
            Object[][][] result = tabulator.tabulate(args[0].substring(1), args[1],
                    -2, 2, -2, 2, -2 ,2);
            for (Object[][] objects : result) {
                for (Object[] object : objects) {
                    for (Object element : object) {
                        System.out.println(element + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}
