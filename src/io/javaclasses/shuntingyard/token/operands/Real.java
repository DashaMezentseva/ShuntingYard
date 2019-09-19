package io.javaclasses.shuntingyard.token.operands;

import io.javaclasses.shuntingyard.token.Operand;

public class Real implements Operand {
    private final double value;

    public Real(final String value) throws NumberFormatException {
        this.value = Double.parseDouble(value);
    }

    public Real(final double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public Operand add(Operand addend) {
        return new Real(value + addend.getValue());
    }

    @Override
    public Operand multiply(Operand multiplicand) {
        return new Real(value * multiplicand.getValue());
    }

    @Override
    public Operand divide(Operand divisor) {
        return new Real(value / divisor.getValue());
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }
}
