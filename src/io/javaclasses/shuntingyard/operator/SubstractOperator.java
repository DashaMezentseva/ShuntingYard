package io.javaclasses.shuntingyard.operator;

public class SubstractOperator extends Operator {

    public static int priority = 1;

    public SubstractOperator() {
        super(1);
    }

    @Override
    public double solve(double numberFirst, double numberSecond) {
        return numberFirst - numberSecond;
    }
}
