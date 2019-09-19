package io.javaclasses.shuntingyard.token.operands;


import io.javaclasses.shuntingyard.exception.EvaluationException;
import io.javaclasses.shuntingyard.token.Operand;
import io.javaclasses.shuntingyard.token.VariableMap;

public class Variable implements Operand {
    private final String symbol;

    public Variable(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public double getValue() throws EvaluationException {
        if (!isInitialized()) {
            throw new EvaluationException("Variable " + symbol + " is not initialized.");
        }
        return VariableMap.INSTANCE.get(this).getValue();
    }

    @Override
    public Operand add(Operand addend) throws EvaluationException {
        return new Real(getValue()).add(addend);
    }

    @Override
    public Operand multiply(Operand multiplicand) throws EvaluationException {
        return new Real(getValue()).multiply(multiplicand);
    }

    @Override
    public Operand divide(Operand divisor) throws EvaluationException {
        return new Real(getValue()).divide(divisor);
    }


    private boolean isInitialized() {
        return VariableMap.INSTANCE.get(this) != null;
    }

    @Override
    public String toString() {
        return symbol + (isInitialized()
                ? " = " + String.format("%.2f", getValue())
                : "");
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && symbol.equals(((Variable) obj).symbol);
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}
