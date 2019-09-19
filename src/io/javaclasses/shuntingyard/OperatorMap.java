package io.javaclasses.shuntingyard;

import io.javaclasses.shuntingyard.token.Operator;
import io.javaclasses.shuntingyard.token.operator.DivideOperator;
import io.javaclasses.shuntingyard.token.operator.MultiplyOperator;
import io.javaclasses.shuntingyard.token.operator.SumOperator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class OperatorMap {
    static final OperatorMap INSTANCE = new OperatorMap();

    private final Map<String, Operator> operationMap;

    private OperatorMap() {
        operationMap = Collections.unmodifiableMap(initializedMap());
    }

    private static Map<String, Operator> initializedMap() {
        Map<String, Operator> map = new HashMap<>();

        // The basic binary operators, used in infix form
        map.put(SumOperator.INSTANCE.getSymbol(), SumOperator.INSTANCE);
        map.put(MultiplyOperator.INSTANCE.getSymbol(), MultiplyOperator.INSTANCE);
        map.put(DivideOperator.INSTANCE.getSymbol(), DivideOperator.INSTANCE);

        return map;
    }

    Operator getFor(final char character) {
        return getFor(String.valueOf(character));
    }

    Operator getFor(final String symbol) {
        return operationMap.get(symbol);
    }

}
