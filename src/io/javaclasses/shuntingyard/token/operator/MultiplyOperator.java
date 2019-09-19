package io.javaclasses.shuntingyard.token.operator;

import io.javaclasses.shuntingyard.exception.ArityException;
import io.javaclasses.shuntingyard.token.Operand;
import io.javaclasses.shuntingyard.token.Operator;

public class MultiplyOperator extends Operator {
    public static final MultiplyOperator INSTANCE = new MultiplyOperator();

    private MultiplyOperator() {
        super("*", 2, MULTIPLICATIVE);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException {
        check(operands.length);
        return operands[0].multiply(operands[1]);
    }
}
