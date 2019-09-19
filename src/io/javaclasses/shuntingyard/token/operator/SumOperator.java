package io.javaclasses.shuntingyard.token.operator;


import io.javaclasses.shuntingyard.exception.ArityException;
import io.javaclasses.shuntingyard.token.Operand;
import io.javaclasses.shuntingyard.token.Operator;

public class SumOperator extends Operator {
    public static final SumOperator INSTANCE = new SumOperator();

    private SumOperator() {
        super("+", 2, ADDITIVE);
    }

    @Override
    public Operand evaluate(Operand... operands) throws ArityException {
        check(operands.length);
        return operands[0].add(operands[1]);
    }
}
