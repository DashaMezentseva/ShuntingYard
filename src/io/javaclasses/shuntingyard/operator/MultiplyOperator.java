package io.javaclasses.shuntingyard.operator;

public class MultiplyOperator extends Operator {

    public static int priority = 0;

    public MultiplyOperator() {
        super(0);
    }

    @Override
    public double solve(double numberFirst, double numberSecond) {
        return numberFirst * numberSecond;
    }
}
