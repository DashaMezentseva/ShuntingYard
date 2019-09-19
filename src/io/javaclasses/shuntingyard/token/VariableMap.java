package io.javaclasses.shuntingyard.token;


import io.javaclasses.shuntingyard.token.operands.Real;
import io.javaclasses.shuntingyard.token.operands.Variable;

import java.util.HashMap;
import java.util.Map;

public class VariableMap {

    public static final VariableMap INSTANCE = new VariableMap();

    private final Map<Variable, Operand> map;

    private VariableMap() {
        map = new HashMap<>();
        // constants
        map.put(new Variable("pi"), new Real(Math.PI));
        map.put(new Variable("e"), new Real(Math.E));
        map.put(new Variable("phi"), new Real((1 + Math.sqrt(5)) / 2));
    }


    public void bind(final Variable variable, final Operand operand) {
        map.put(variable, operand);
    }

    public Operand get(final Variable variable) {
        return map.get(variable);
    }
}
