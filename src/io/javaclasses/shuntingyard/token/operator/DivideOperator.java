package io.javaclasses.shuntingyard.token.operator;

import io.javaclasses.shuntingyard.exception.ArityException;
import io.javaclasses.shuntingyard.token.Operand;
import io.javaclasses.shuntingyard.token.Operator;

public class DivideOperator extends Operator {
    public static final DivideOperator INSTANCE = new DivideOperator();

    private DivideOperator() {
        super("/", 2, MULTIPLICATIVE);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException {
        check(operands.length);
        return operands[0].divide(operands[1]);
    }
}
